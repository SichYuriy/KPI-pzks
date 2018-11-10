package com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.partreader;

import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.ParenthesisExpression;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.ParenthesisToken;
import com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.ParenthesisExpressionTransformer;
import com.gmail.at.sichyuriyy.computer.systems.token.Token;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

import static com.gmail.at.sichyuriyy.computer.systems.passingbrackets.transformer.partreader.TokenPartReaderUtils.findExpressionEndIndex;
import static com.gmail.at.sichyuriyy.computer.systems.token.TokenType.isOpenParen;

@AllArgsConstructor
public class ExpressionTokenPartReader implements TokenPartReader {

    private ParenthesisExpressionTransformer transformer;

    @Override
    public boolean canRead(Token token) {
        return isOpenParen(token);
    }

    @Override
    public int readTokenPart(ArrayList<Token> tokens, int nextTokenIndex, boolean multiply, ParenthesisToken outputToken) {
        int closingParen = findExpressionEndIndex(tokens, nextTokenIndex);
        ParenthesisExpression exp = transformer.transform(new ArrayList<>(tokens.subList(nextTokenIndex + 1, closingParen)));
        outputToken.addExpression(multiply, exp);
        return closingParen + 1;
    }
}
