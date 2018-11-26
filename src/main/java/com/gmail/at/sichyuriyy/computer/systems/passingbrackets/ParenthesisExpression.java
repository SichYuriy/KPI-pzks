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
        ParenthesisExpression other = (ParenthesisExpression) object;
        return this.terms.containsAll(other.getTerms())
                && other.getTerms().containsAll(this.terms);
    }

    @Override
    public int hashCode() {
        return terms.stream()
                .mapToInt(ParenthesisToken::hashCode)
                .sum();
    }

    public ParenthesisExpression multiply(ParenthesisExpression other) {
        ArrayList<ParenthesisToken> resultTerms = new ArrayList<>();
        for (ParenthesisToken token1: this.getTerms()) {
            for (ParenthesisToken token2: other.getTerms()) {
                resultTerms.add(token1.multiply(token2));
            }
        }
        return new ParenthesisExpression(resultTerms);
    }

    public ParenthesisExpression multiplyToken(ParenthesisToken token) {
        ArrayList<ParenthesisToken> resultTerms = new ArrayList<>();
        for (ParenthesisToken term: this.getTerms()) {
            ParenthesisToken resultToken = new ParenthesisToken();
            resultToken.setNegative(term.isNegative() ^ token.isNegative());
            resultToken.addAll(term);
            resultToken.addAll(token);
            resultTerms.add(resultToken);
        }
        return new ParenthesisExpression(resultTerms);
    }

    public ParenthesisExpression divideEach(ParenthesisExpression divExp) {
        ArrayList<ParenthesisToken> resultTerms = new ArrayList<>();
        for (ParenthesisToken token: this.getTerms()) {
            ParenthesisToken clone = token.makeClone();
            clone.getDivideExpressions().add(divExp);
            resultTerms.add(clone);
        }
        return new ParenthesisExpression(resultTerms);
    }
}
