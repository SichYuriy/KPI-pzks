package com.gmail.at.sichyuriyy.computer.systems.token.reader;

import com.gmail.at.sichyuriyy.computer.systems.token.Token;

public interface TokenReader {
    boolean canRead(String str, int startPosition);
    Token readToken(String  str, int startPosition);
}
