package com.college.tree;

public class AVLNode extends Node{
    public int height;

    public AVLNode(int value){
        super(value);
        this.height = 1;

    }
}
