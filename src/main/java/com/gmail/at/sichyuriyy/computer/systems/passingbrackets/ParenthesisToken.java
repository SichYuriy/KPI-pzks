package com.gmail.at.sichyuriyy.computer.systems.passingbrackets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.join;
import static java.util.stream.Collectors.toList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParenthesisToken {

    private boolean negative;
    @Builder.Default
    private List<String> multiplyVars = new ArrayList<>();
    @Builder.Default
    private List<String> divideVars = new ArrayList<>();
    @Builder.Default
    private List<ParenthesisExpression> multiplyExpressions = new ArrayList<>();
    @Builder.Default
    private List<ParenthesisExpression> divideExpressions = new ArrayList<>();
    @Builder.Default
    private List<FunctionExpression> multiplyFunctions = new ArrayList<>();
    @Builder.Default
    private List<FunctionExpression> divideFunctions = new ArrayList<>();

    public void addValue(boolean multiply, String val) {
        if (val.equals("1")) {
            return;
        }
        if (multiply) {
            multiplyVars.add(val);
        } else {
            divideVars.add(val);
        }
    }

    public void addExpression(boolean multiply, ParenthesisExpression expression) {
        if (multiply) {
            multiplyExpressions.add(expression);
        } else {
            divideExpressions.add(expression);
        }
    }

    public void addFunction(boolean multiply, String value, ParenthesisExpression exp) {
        FunctionExpression func = new FunctionExpression(value, exp);
        if (multiply) {
            multiplyFunctions.add(func);
        } else {
            divideFunctions.add(func);
        }
    }

    public int getPartsCount() {
        return multiplyVars.size()
                + divideVars.size()
                + multiplyExpressions.size()
                + divideExpressions.size()
                + multiplyFunctions.size()
                + divideFunctions.size();
    }

    public boolean isEmpty() {
        return multiplyVars.isEmpty()
                && divideVars.isEmpty()
                && multiplyExpressions.isEmpty()
                && divideExpressions.isEmpty()
                && multiplyFunctions.isEmpty()
                && divideFunctions.isEmpty();
    }

    public void addAll(ParenthesisToken parenthesisToken) {
        this.getMultiplyVars().addAll(parenthesisToken.getMultiplyVars());
        this.getDivideVars().addAll(parenthesisToken.getDivideVars());
        this.getMultiplyExpressions().addAll(parenthesisToken.getMultiplyExpressions());
        this.getDivideExpressions().addAll(parenthesisToken.getDivideExpressions());
        this.getMultiplyFunctions().addAll(parenthesisToken.getMultiplyFunctions());
        this.getDivideFunctions().addAll(parenthesisToken.getDivideFunctions());
    }

    public ParenthesisToken getCommon(ParenthesisToken other) {
        ParenthesisToken result = new ParenthesisToken();
        result.setMultiplyVars(getCommon(this.getMultiplyVars(), other.getMultiplyVars()));
        result.setDivideVars(getCommon(this.getDivideVars(), other.getDivideVars()));
        result.setMultiplyExpressions(getCommon(this.getMultiplyExpressions(), other.getMultiplyExpressions()));
        result.setDivideExpressions(getCommon(this.getDivideExpressions(), other.getDivideExpressions()));
        result.setMultiplyFunctions(getCommon(this.getMultiplyFunctions(), other.getMultiplyFunctions()));
        result.setDivideFunctions(getCommon(this.getDivideFunctions(), other.getDivideFunctions()));
        return result;
    }

    private <E> List<E> getCommon(List<E> list1, List<E> list2) {
        return list1.stream()
                .filter(list2::contains)
                .collect(toList());
    }

    private <E> List<E> extract(List<E> list1, List<E> list2) {
        return list1.stream()
                .filter(el -> !list2.contains(el))
                .collect(toList());
    }

    public ParenthesisToken extract(ParenthesisToken extractToken) {
        ParenthesisToken result = new ParenthesisToken();
        result.setNegative(this.negative);
        result.setMultiplyVars(extract(this.getMultiplyVars(), extractToken.getMultiplyVars()));
        result.setDivideVars(extract(this.getDivideVars(), extractToken.getDivideVars()));
        result.setMultiplyExpressions(extract(this.getMultiplyExpressions(), extractToken.getMultiplyExpressions()));
        result.setDivideExpressions(extract(this.getDivideExpressions(), extractToken.getDivideExpressions()));
        result.setMultiplyFunctions(extract(this.getMultiplyFunctions(), extractToken.getMultiplyFunctions()));
        result.setDivideFunctions(extract(this.getDivideFunctions(), extractToken.getDivideFunctions()));
        return result;
    }

    public boolean isExpression() {
        return getPartsCount() == 1
                && !multiplyExpressions.isEmpty();
    }

    public ParenthesisToken makeClone() {
        ParenthesisToken clone = new ParenthesisToken();
        clone.setNegative(this.negative);
        clone.addAll(this);
        return clone;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return negative ? "-1" : "+1";
        }
        StringBuilder result = new StringBuilder();
        result.append(negative ? '-' : '+');
        boolean empty = true;
        if (!multiplyVars.isEmpty()) {
            empty = false;
            result.append(join("*", multiplyVars));
        }
        if (!multiplyFunctions.isEmpty()) {
            if (!empty) result.append("*");
            empty = false;
            result.append(join("*", multiplyFunctions.stream().map(Object::toString).collect(toList())));
        }
        if (!multiplyExpressions.isEmpty()) {
            if (!empty) result.append("*");
            empty = false;
            List<String> multiplyExpList = multiplyExpressions.stream()
                    .map(e -> "(" + e.toString() + ")")
                    .collect(toList());
            result.append(join("*", multiplyExpList));
        }
        if (empty) {
            result.append("1");
        }
        divideVars.forEach(var -> result.append("/").append(var));
        divideFunctions.forEach(func -> result.append("/").append(func));
        divideExpressions.forEach(exp -> result.append("/(").append(exp).append(")"));
        return result.toString();
    }

}