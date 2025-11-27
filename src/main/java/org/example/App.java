package org.example;

import org.example.composite.SymbolLeaf;
import org.example.composite.TextType;

public class App {
    public static void main( String[] args ) {
        SymbolLeaf s1 = new SymbolLeaf('a', TextType.SYMBOL);
        SymbolLeaf s2 = new SymbolLeaf(' ', TextType.SYMBOL);
        SymbolLeaf s3 = new SymbolLeaf('b', TextType.SYMBOL);
    }
}
