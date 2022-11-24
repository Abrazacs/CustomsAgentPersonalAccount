package ru.ssemenov.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.ssemenov.entities.CustomsDeclaration;

public class CustomsDeclarationSpecifications {
    public static Specification<CustomsDeclaration> numberLike(String numberPart) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("number"), String.format("%%%s%%", numberPart));
    }

    public static Specification<CustomsDeclaration> equalsVatCode(String vatCode) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("vatCode"), vatCode);
    }
}
