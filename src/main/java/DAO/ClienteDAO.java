package DAO;

import Entidades.Cliente;
import config.JpaUtil;
import interfacez.IClienteDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClienteDAO implements IClienteDAO {

    private static final int MAX_LIST_LIMIT = 100;

    /**
     * Valida y ajusta el límite de resultados.
     *
     * @param limit El límite solicitado.
     * @return El límite ajustado (entre 1 y MAX_LIST_LIMIT).
     */
    private int getValidLimit(int limit) {
        if (limit <= 0) {
            return 1;
        }
        return Math.min(limit, MAX_LIST_LIMIT);
    }

    @Override
    public void crear(Cliente cliente) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(cliente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            System.err.println("Error al crear cliente: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Cliente buscarPorId(Long id) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.find(Cliente.class, id);
        } catch (Exception e) {
            System.err.println("Error al buscar cliente por ID: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> listar(int limit) {
        int validLimit = getValidLimit(limit);

        EntityManager em = JpaUtil.createEntityManager();
        try {
            String jpql = "SELECT c FROM Cliente c ORDER BY c.nombreCompleto.nombre";
            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            query.setMaxResults(validLimit); // Aplica el límite
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizar(Cliente cliente) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(cliente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar cliente: " + e.getMessage());
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
            Cliente cliente = em.find(Cliente.class, id);
            if (cliente != null) {
                em.remove(cliente);
            } else {
                System.out.println("No se encontró el cliente con ID: " + id + " para eliminar.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> buscarPorPreferencia(String preferencia, int limit) {
        int validLimit = getValidLimit(limit);
        EntityManager em = JpaUtil.createEntityManager();
        try {

            String jpql = "SELECT c FROM Cliente c JOIN c.preferencias p WHERE p = :pref";

            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            query.setParameter("pref", preferencia);
            query.setMaxResults(validLimit);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al buscar por preferencia: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> clientesConMayorGasto(int limit) {
        int validLimit = getValidLimit(limit);
        EntityManager em = JpaUtil.createEntityManager();
        try {

            String jpql = "SELECT c, SUM(phd.precioVenta * phd.cantidad) AS gastoTotal "
                    + "FROM Cliente c "
                    + "JOIN c.pedidos p "
                    + "JOIN p.detalle phd "
                    + "GROUP BY c "
                    + "ORDER BY gastoTotal DESC";

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setMaxResults(validLimit);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al obtener clientes con mayor gasto: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> clientesSinPedidos(int limit) {
        int validLimit = getValidLimit(limit);
        EntityManager em = JpaUtil.createEntityManager();
        try {
            String jpql = "SELECT c FROM Cliente c WHERE c.pedidos IS EMPTY";

            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            query.setMaxResults(validLimit);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al obtener clientes sin pedidos: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> clientesRecomendadosPor(Long clienteId, int limit) {
        int validLimit = getValidLimit(limit);
        EntityManager em = JpaUtil.createEntityManager();
        try {
            String jpql = "SELECT c FROM Cliente c WHERE c.clienteRecomienda.num_cliente = :idRecomendador";

            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            query.setParameter("idRecomendador", clienteId);
            query.setMaxResults(validLimit);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al obtener clientes recomendados: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }
}
