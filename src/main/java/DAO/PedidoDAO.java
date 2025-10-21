package DAO;

import Entidades.Cliente;
import Entidades.Pedido;
import Entidades.PedidoHotDog;
import config.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

/**
 * Implementación del DAO para la entidad Pedido.
 */
public class PedidoDAO implements IPedidoDAO {

    private static final int MAX_LIST_LIMIT = 100;

    private int getValidLimit(int limit) {
        if (limit <= 0) {
            return 1;
        }
        return Math.min(limit, MAX_LIST_LIMIT);
    }

    @Override
    public void crear(Pedido pedido) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            if (pedido.getDetalle() != null) {
                for (PedidoHotDog phd : pedido.getDetalle()) {
                    phd.setPedido(pedido);
                }
            }

            em.persist(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al crear pedido: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Pedido buscarPorId(Long id) {
        EntityManager em = JpaUtil.createEntityManager();
        try {

            String jpql = "SELECT p FROM Pedido p LEFT JOIN FETCH p.detalle WHERE p.id = :id";
            TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Error al buscar pedido por ID (o no encontrado): " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pedido> listar(int limit) {
        int validLimit = getValidLimit(limit);
        EntityManager em = JpaUtil.createEntityManager();
        try {

            String jpql = "SELECT p FROM Pedido p JOIN FETCH p.cliente ORDER BY p.fecha DESC";
            TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
            query.setMaxResults(validLimit);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizar(Pedido pedido) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            if (pedido.getDetalle() != null) {
                for (PedidoHotDog phd : pedido.getDetalle()) {
                    phd.setPedido(pedido);
                }
            }

            em.merge(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar pedido: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Long id) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Pedido pedido = em.find(Pedido.class, id);
            if (pedido != null) {
                em.remove(pedido);
            } else {
                System.out.println("No se encontró el pedido con ID: " + id + " para eliminar.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar pedido: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Double ticketPromedio() {
        EntityManager em = JpaUtil.createEntityManager();
        try {

            String jpqlTotalVentas = "SELECT SUM(phd.precioVenta * phd.cantidad) FROM PedidoHotDog phd";
            String jpqlTotalPedidos = "SELECT COUNT(p) FROM Pedido p";

            Double totalVentas = em.createQuery(jpqlTotalVentas, Double.class).getSingleResult();
            Long totalPedidos = em.createQuery(jpqlTotalPedidos, Long.class).getSingleResult();

            if (totalPedidos == 0 || totalVentas == null) {
                return 0.0;
            }

            return totalVentas / totalPedidos;

        } catch (Exception e) {
            System.err.println("Error al calcular ticket promedio: " + e.getMessage());
            return 0.0;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> pedidosPorMetodoPago() {
        EntityManager em = JpaUtil.createEntityManager();
        try {

            String jpql = "SELECT p.metodoPago, COUNT(p) "
                    + "FROM Pedido p "
                    + "GROUP BY p.metodoPago";

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al agrupar pedidos por método de pago: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pedido> pedidosConFiltros(int minArticulos, BigDecimal totalMinimo, int limit) {
        int validLimit = getValidLimit(limit);
        EntityManager em = JpaUtil.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Pedido> cq = cb.createQuery(Pedido.class);
            Root<Pedido> pedido = cq.from(Pedido.class);

            Join<Pedido, PedidoHotDog> detalle = pedido.join("detalle");

            cq.groupBy(pedido.get("id"));

            Predicate havingCantidad = cb.greaterThan(
                    cb.sum(detalle.get("cantidad")), minArticulos
            );

            Predicate havingTotal = cb.greaterThanOrEqualTo(
                    cb.sum(cb.prod(detalle.get("precioVenta"), detalle.get("cantidad"))),
                    totalMinimo
            );

            cq.select(pedido).having(cb.and(havingCantidad, havingTotal));

            TypedQuery<Pedido> query = em.createQuery(cq);
            query.setMaxResults(validLimit);
            return query.getResultList();

        } catch (Exception e) {
            System.err.println("Error al buscar pedidos con filtros: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    @Override
    public Pedido ultimoPedidoPorCliente(Cliente cliente) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Pedido> cq = cb.createQuery(Pedido.class);
            Root<Pedido> pedido = cq.from(Pedido.class);

            cq.where(cb.equal(pedido.get("cliente"), cliente));

            cq.orderBy(cb.desc(pedido.get("fecha")));

            TypedQuery<Pedido> query = em.createQuery(cq);
            query.setMaxResults(1);

            return query.getResultStream().findFirst().orElse(null);

        } catch (Exception e) {
            System.err.println("Error al obtener último pedido por cliente: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }
}
