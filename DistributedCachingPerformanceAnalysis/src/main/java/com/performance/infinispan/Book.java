/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performance.infinispan;

/**
 *
 * @author Fayez
 */
import java.io.Serializable;
import org.hibernate.search.annotations.Indexed;
 
@Indexed
public class Book implements Serializable {
     
     String title;
     String author;
     String editor;
    public Book(String title, String author, String editor) {
        this.title = title;
        this.author = author;
        this.editor = editor;
    }
    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + ", editor="
                + editor + "]";
    }
}