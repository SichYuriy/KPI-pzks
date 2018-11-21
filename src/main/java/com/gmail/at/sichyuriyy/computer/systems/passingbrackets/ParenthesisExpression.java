package com.gmail.at.sichyuriyy.computer.systems.passingbrackets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParenthesisExpression {
    private ArrayList<ParenthesisToken> terms;

    public ParenthesisExpression makeClone() {
        ParenthesisExpression clone = new ParenthesisExpression();
        ArrayList<ParenthesisToken> cloneTerms = new ArrayList<>();
        terms.stream()
                .map(ParenthesisToken::makeClone)
                .forEachOrdered(cloneTerms::add);
        clone.setTerms(cloneTerms);
        return clone;
    }

    @Override
    public String toString() {
        if (terms.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        this.terms.stream()
                .map(ParenthesisToken::toString)
                .forEach(result::append);
        if ('+' == result.charAt(0)) {
            result.deleteCharAt(0);
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ParenthesisExpression)) {
            return false;
        }
        return this.terms.containsAll(((ParenthesisExpression) object).getTerms());
    }

    @Override
    public int hashCode() {
        return terms.stream()
                .mapToInt(ParenthesisToken::hashCode)
                .sum();
    }

}
