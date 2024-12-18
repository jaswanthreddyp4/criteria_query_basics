package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.entities.Product;
import org.example.persistence.MyOwnPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.*;

public class  Main {
    public static void main(String[] args) {
        Map<String,String> propertyMap = new HashMap<>();

        //if you wanna see what queries goes to database
        propertyMap.put("hibernate.show_sql","true");

        EntityManagerFactory emf =new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new MyOwnPersistenceUnitInfo(),propertyMap);

        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();

            CriteriaBuilder builder = em.getCriteriaBuilder();

            /* - select all from product

            CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
            Root<Product> productRoot=criteriaQuery.from(Product.class);
            criteriaQuery.select(productRoot);

             */

            /* - selct only product type and price

            CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
            Root<Product> productRoot = criteriaQuery.from(Product.class);
            criteriaQuery.multiselect(productRoot.get("productCatagory"),productRoot.get("price"));

             */

            /* - prod category and their avg prices

            CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);

            Root<Product> productRoot = criteriaQuery.from(Product.class);
            criteriaQuery.multiselect(builder.avg(productRoot.get("price")),productRoot.get("productCatagory"))
                    .groupBy(productRoot.get("productCatagory"));

             */

            /* - category by male

            CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
            Root<Product> productRoot = criteriaQuery.from(Product.class);
            String gender = "m";
            if(gender.equals("m")) {
                criteriaQuery
                        .select(productRoot)
                        .where(builder.equal(productRoot.get("gender"),gender));
            }else{
                criteriaQuery
                        .select(productRoot);
            }


             */

            /* products value <1500 and >1000

            CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
            Root<Product> productRoot = criteriaQuery.from(Product.class);
            criteriaQuery
                    .select(productRoot)
                            .where(builder.and(
                                    builder.le(productRoot.get("price"),1500),
                                    builder.ge(productRoot.get("price"),1000))
                            )
                                    .orderBy(builder.asc(productRoot.get("price")));

             */

            /*
            String gender="f";
            String catagory="shirt";
            CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
            Root<Product> productRoot = criteriaQuery.from(Product.class);

            List<Predicate> predicateList = new ArrayList<>();

            if(gender!=null && !gender.isEmpty()){
                predicateList.add(builder.equal(productRoot.get("gender"), gender));
            }

            if(catagory!=null && !catagory.isEmpty()){
                predicateList.add(builder.equal(productRoot.get("productCatagory"), catagory));
            }

            if(!predicateList.isEmpty()){
                //combines all filters into a where clause
                criteriaQuery.where(builder.and(predicateList.toArray(new Predicate[0])));
            }

            criteriaQuery
                    .select(productRoot)
                    .orderBy(builder.asc(productRoot.get("id")));


            TypedQuery<Product> query = em.createQuery(criteriaQuery);

            query.getResultList().forEach(System.out::println);

             */

            /*

            int x=1000;
            int y=2000;
            String gender="m";
            CriteriaQuery<Product> criteriaQuery= builder.createQuery(Product.class);
            Root<Product> productRoot = criteriaQuery.from(Product.class);
            List<Predicate> predicateList = new ArrayList<>();

            if(x!=0 && y!=0){
                predicateList.add(builder.and(
                        builder.le(productRoot.get("price"),y),
                        builder.ge(productRoot.get("price"),x)
                ));
            }

            if(gender!=null && !gender.isEmpty()){
                predicateList.add(builder.equal(productRoot.get("gender"),gender));
            }
            criteriaQuery.select(productRoot);

            TypedQuery<Product> query = em.createQuery(criteriaQuery);
            query.getResultList().forEach(System.out::println);

             */



            em.getTransaction().commit();
        }finally{
            em.close();
        }
    }
}