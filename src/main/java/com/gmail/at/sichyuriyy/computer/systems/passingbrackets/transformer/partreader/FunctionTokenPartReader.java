package com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.partreader;

import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.ParenthesisExpression;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.ParenthesisToken;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.ParenthesisExpressionTransformer;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

import static com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.partreader.TokenPartReaderUtils.findExpressionEndIndex;
import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.isFunction;

@AllArgsConstructor
public class FunctionTokenPartReader implements TokenPartReader {

    private ParenthesisExpressionTransformer transformer;

    @Override
    public boolean canRead(Token token) {
        return isFunction(token);
    }

    @Override
    public int readTokenPart(ArrayList<Token> tokens, int nextTokenIndex, boolean multiply, ParenthesisToken outputToken) {
        int closingParen = findExpressionEndIndex(tokens, nextTokenIndex + 1);
        ParenthesisExpression exp = transformer.transform(new ArrayList<>(tokens.subList(nextTokenIndex + 2, closingParen)));
        outputToken.addFunction(multiply, tokens.get(nextTokenIndex).getValue(), exp);
        return closingParen + 1;
    }
}
