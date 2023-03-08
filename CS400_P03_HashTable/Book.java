//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: P03 Hash Table
// Files: BookHashTable.java, BookHashTableTest.java, Book.java, 
//        HashTableADT.java, BookParser.java
// Course: CS400, Fall Semester, 2019
//
// Author: Salvatore Mingari
// Email: mingari@wisc.edu
// Lecturer's Name: Deb Deppeler
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

public class Book {
    private String isbn13;
    private String authors; 
    private String original_publication_year;
    private String title; 
    private String language_code;
    private String average_rating;
    private String cover_type; 
    private String pages;
    
    public Book(String isbn13, String authors, 
            String original_publication_year, String title,
            String language_code, String average_rating, 
            String cover_type, String pages){
        this.isbn13 = isbn13; 
        this.title = title;
        this.authors = authors; 
        this.original_publication_year = original_publication_year;
        this.language_code = language_code; 
        this.average_rating = average_rating;
        this.cover_type = cover_type; 
        this.pages = pages; 
    }

    public String getKey() {
        return this.isbn13;
    }
    
    public void setKey(String isbn13) {
        this.isbn13 = isbn13;
    }
    
    
    public String getIsbn13() {
      return isbn13;
    }

    public String getAuthors() {
      return authors;
    }

    public String getOriginal_publication_year() {
      return original_publication_year;
    }

    public String getTitle() {
      return title;
    }

    public String getLanguage_code() {
      return language_code;
    }

    public String getAverage_rating() {
      return average_rating;
    }

    public String getCover_type() {
      return cover_type;
    }

    public String getPages() {
      return pages;
    }

    @Override
    public String toString() {
        return "ISBN13: "+this.isbn13+"; Book: "+ 
               this.title+", Author: "+this.authors+
               ", Original Publication Year: "+
               this.original_publication_year+
               ", Language: "+this.language_code+", Average Rating: "+
               this.average_rating+", Cover Type: "+this.cover_type+ 
               ", Pages: "+ this.pages;                
    }
}

