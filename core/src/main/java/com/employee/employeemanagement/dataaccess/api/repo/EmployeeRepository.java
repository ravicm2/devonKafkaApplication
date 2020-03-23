package com.employee.employeemanagement.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.util.Iterator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.employee.employeemanagement.dataaccess.api.EmployeeEntity;
import com.employee.employeemanagement.logic.api.to.EmployeeSearchCriteriaTo;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * {@link DefaultRepository} for {@link EmployeeEntity}
 */
public interface EmployeeRepository extends DefaultRepository<EmployeeEntity> {

  /**
   * @param criteria the {@link EmployeeSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link EmployeeEntity} objects that matched the search. If no pageable is set, it
   *         will return a unique page with all the objects that matched the search.
   */
  default Page<EmployeeEntity> findByCriteria(EmployeeSearchCriteriaTo criteria) {

    EmployeeEntity alias = newDslAlias();
    JPAQuery<EmployeeEntity> query = newDslQuery(alias);

    String name = criteria.getName();
    if (name != null && !name.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getName()), name, criteria.getNameOption());
    }
    Integer age = criteria.getAge();
    if (age != null) {
      query.where($(alias.getAge()).eq(age));
    }
    String location = criteria.getLocation();
    if (location != null && !location.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getLocation()), location, criteria.getLocationOption());
    }
    Boolean validEmployee = criteria.getValidEmployee();
    if (validEmployee != null) {
      query.where($(alias.getValidEmployee()).eq(validEmployee));
    }
    if (criteria.getPageable() == null) {
      criteria.setPageable(PageRequest.of(0, Integer.MAX_VALUE));
    } else {
      addOrderBy(query, alias, criteria.getPageable().getSort());
    }

    return QueryUtil.get().findPaginated(criteria.getPageable(), query, true);
  }

  /**
   * Add sorting to the given query on the given alias
   *
   * @param query to add sorting to
   * @param alias to retrieve columns from for sorting
   * @param sort specification of sorting
   */
  public default void addOrderBy(JPAQuery<EmployeeEntity> query, EmployeeEntity alias, Sort sort) {

    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch (next.getProperty()) {
          case "name":
            if (next.isAscending()) {
              query.orderBy($(alias.getName()).asc());
            } else {
              query.orderBy($(alias.getName()).desc());
            }
            break;
          case "age":
            if (next.isAscending()) {
              query.orderBy($(alias.getAge()).asc());
            } else {
              query.orderBy($(alias.getAge()).desc());
            }
            break;
          case "location":
            if (next.isAscending()) {
              query.orderBy($(alias.getLocation()).asc());
            } else {
              query.orderBy($(alias.getLocation()).desc());
            }
            break;
          case "validEmployee":
            if (next.isAscending()) {
              query.orderBy($(alias.getValidEmployee()).asc());
            } else {
              query.orderBy($(alias.getValidEmployee()).desc());
            }
            break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '" + next.getProperty() + "'");
        }
      }
    }
  }

}