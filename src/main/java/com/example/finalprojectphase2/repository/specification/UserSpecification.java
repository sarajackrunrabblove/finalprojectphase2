package com.example.finalprojectphase2.repository.specification;

import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.payload.UserDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> findByFilters(UserDTO payload) {
        return (user, cq, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (payload.getFirstName() != null)
                predicates.add(cb.like(user.get("firstName"), "%" + payload.getFirstName() + "%"));

            if (payload.getLastName() != null)
                predicates.add(cb.like(user.get("lastName"), "%" + payload.getLastName() + "%"));

            if (payload.getUserName() != null)
                predicates.add(cb.like(user.get("userName"), "%" + payload.getUserName() + "%"));

            if (payload.getRole() != null)
                predicates.add(cb.equal(user.get("role"), payload.getRole()));

            if (payload.getEmail() != null)
                predicates.add(cb.like(user.get("email"), "%" + payload.getEmail() + "%"));

            if (payload.getStatus() != null)
                predicates.add(cb.equal(user.get("status"), payload.getStatus()));

            if (payload.getRegistrationDate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate regDate = LocalDate.parse(payload.getRegistrationDate(), formatter);
                predicates.add(cb.equal(user.get("registrationDate"), regDate));
            }

            if (payload.getCredit() != null)
                predicates.add(cb.greaterThanOrEqualTo(user.get("credit"), payload.getCredit()));

            if (payload.getRate() != null)
                predicates.add(cb.greaterThanOrEqualTo(user.get("rate"), payload.getRate()));

            if (payload.getExpertSkills() != null && !payload.getExpertSkills().isEmpty())
                predicates.add(user.join("expertSkills")
                        .get("id")
                        .in(payload.getExpertSkills()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
