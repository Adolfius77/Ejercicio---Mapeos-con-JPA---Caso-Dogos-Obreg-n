package DAO;

import Entidades.HotDog;
import Entidades.PedidoHotDog;
import config.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

/**
 * Implementación del DAO para la entidad HotDog.
 */
public class HotDogDAO implements IHotDogDAO {
    
    private static final int MAX_LIST_LIMIT = 100;

    private int getValidLimit(int limit) {
        if (limit <= 0) {
            return 1;
        }
        return Math.min(limit, MAX_LIST_LIMIT);
    }
    
    @Override
    public void crear(HotDog hotDog) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(hotDog);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al crear hotdog: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public HotDog buscarPorId(Long id) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.find(HotDog.class, id);
        } catch (Exception e) {
            System.err.println("Error al buscar hotdog por ID: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<HotDog> listar(int limit) {
        int validLimit = getValidLimit(limit);
        EntityManager em = JpaUtil.createEntityManager();
        try {
            String jpql = "SELECT h FROM HotDog h ORDER BY h.nombre";
            TypedQuery<HotDog> query = em.createQuery(jpql, HotDog.class);
            query.setMaxResults(validLimit);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar hotdogs: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizar(HotDog hotDog) {
        EntityManager em = JpaUtil.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(hotDog);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar hotdog: " + e.getMessage());
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
            HotDog hotDog = em.find(HotDog.class, id);
            if (hotDog != null) {
                em.remove(hotDog);
            } else {
                System.out.println("No se encontró el hotdog con ID: " + id + " para eliminar.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar hotdog: " + e.getMessage());
        } finally {
            em.close();
        }
    }


    @Override
    public List<Object[]> hotDogsMasVendidos(int limit) {
        int validLimit = getValidLimit(limit);
        EntityManager em = JpaUtil.createEntityManager();
        try {
            String jpql = "SELECT h, SUM(phd.cantidad) AS totalVendido " +
                          "FROM HotDog h " +
                          "JOIN h.pedidosDondeAparece phd " + 
                          "GROUP BY h " +
                          "ORDER BY totalVendido DESC";
            
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setMaxResults(validLimit);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al obtener hotdogs más vendidos: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    @Override
    public List<HotDog> buscarPorRangoPrecio(BigDecimal min, BigDecimal max, int limit) {
        int validLimit = getValidLimit(limit);
        EntityManager em = JpaUtil.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<HotDog> cq = cb.createQuery(HotDog.class);
            Root<HotDog> hotdog = cq.from(HotDog.class);

            cq.select(hotdog)
              .where(cb.between(hotdog.get("precio"), min, max));
            
            cq.orderBy(cb.asc(hotdog.get("precio")));

            TypedQuery<HotDog> query = em.createQuery(cq);
            query.setMaxResults(validLimit);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al buscar hotdogs por rango de precio: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<HotDog> hotdogsSinVentas(int limit) {
        int validLimit = getValidLimit(limit);
        EntityManager em = JpaUtil.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<HotDog> cq = cb.createQuery(HotDog.class);
            Root<HotDog> hotdog = cq.from(HotDog.class);

            cq.select(hotdog)
              .where(cb.isEmpty(hotdog.get("pedidosDondeAparece")));

            TypedQuery<HotDog> query = em.createQuery(cq);
            query.setMaxResults(validLimit);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al buscar hotdogs sin ventas: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }
}