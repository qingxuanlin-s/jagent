package com.lqx.fort;

import com.sun.tools.javac.parser.Scanner;
import com.sun.tools.javac.parser.ScannerFactory;
import com.sun.tools.javac.parser.Tokens;
import com.sun.tools.javac.util.Context;

public class JavaJST {
    public static void main(String[] args) {
        ScannerFactory instance = ScannerFactory.instance(new Context());
        Scanner scanner = instance.newScanner("for (Integer integer : x) {       yBuilder.append(\" \").append(integer);  }", false);

        while (true) {
            scanner.nextToken();

            Tokens.Token token = scanner.token();

            String tok = null;
            try {
                tok = token.name().toString();
            }catch (Exception e){
            }
            System.out.println(tok != null? tok : token.kind.toString());
            if(token.kind == Tokens.TokenKind.EOF){
                break;
            }
        }
    }
}
