package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String ALL_BOOKS_KEY= "all_books";
    private static final String ALREADY_READ_BOOKS= "already_read_books";
    private static final String WANT_TO_READ_BOOKS= "want_to_read_books";
    private static final String CURRENTLY_READING_BOOKS= "currently_reading_books";
    private static final String FAVOURITE_BOOKS= "favourite_books";


    public static Utils instance;
    private SharedPreferences sharedPreferences;



    //private static ArrayList<Book> allBooks;
    //private static ArrayList<Book> alreadyReadBooks;
    //private static ArrayList<Book> wantToReadBooks;
    //private static ArrayList<Book> currentlyReadBooks;
    //private static ArrayList<Book> favouriteBooks;


    private Utils(Context context){


        sharedPreferences = context.getSharedPreferences("alternare_db", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyReadBooks()){
            editor.putString(ALREADY_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();


        }
        if (null == getAllBooks()){

            initData();

        }
        if (null == getWantToReadBooks()){
            editor.putString(WANT_TO_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();


        }
        if (null == getCurrentlyReadBooks()){
            editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();


        }
        if (null == getFavouriteBooks()){
            editor.putString(FAVOURITE_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();


        }

    }

    private void initData() {

        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "An abundance of Katherines", "John Green", 256,"https://images-na.ssl-images-amazon.com/images/I/71I6ooyL2LL.jpg", "a child prodigy and a wannabe genius, who only seems to want to date girls named Katherine",
                "long desc"));
        books.add(new Book(2, "The Davinci Code", "Dan Brown", 416,"https://images-na.ssl-images-amazon.com/images/I/A15FFg6aNLL.jpg", "The greatest conspiracy of the past two thousand years is about to unravel.",
                "long desc"));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit();

    }

    public static Utils getInstance(Context context) {
        if (null != instance){
            return instance;
        }else {
            instance = new Utils(context);
            return instance;
        }

    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type  = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type );
        return books;
    }

    public  ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type  = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS, null), type );
        return books;
    }

    public  ArrayList<Book> getWantToReadBooks() {
        Gson gson = new Gson();
        Type type  = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS, null), type );
        return books;
    }

    public ArrayList<Book> getCurrentlyReadBooks() {
        Gson gson = new Gson();
        Type type  = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS, null), type );
        return books;
    }

    public ArrayList<Book> getFavouriteBooks() {
        Gson gson = new Gson();
        Type type  = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVOURITE_BOOKS, null), type );
        return books;
    }

    public  Book getBookById(int id) {
        ArrayList<Book> books = getAllBooks();
        if (null!= books){
            for (Book b: books){
                if (b.getId()==id){
                    return b;
                }
            }
        }

        return null;
    }

    public boolean addToAlreadyRead(Book book){

        ArrayList<Book> books = getAlreadyReadBooks();
        if (null!= books){
            if (books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS);
                editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToWantToRead(Book book){
        ArrayList<Book> books = getWantToReadBooks();
        if (null!= books){
            if (books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOKS);
                editor.putString(WANT_TO_READ_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavourite(Book book){
        ArrayList<Book> books = getFavouriteBooks();
        if (null!= books){
            if (books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVOURITE_BOOKS);
                editor.putString(FAVOURITE_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurrentlyReading(Book book){
        ArrayList<Book> books = getCurrentlyReadBooks();
        if (null!= books){
            if (books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS);
                editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeFromAlreadyReadBooks (Book book){
        ArrayList<Book> books = getAlreadyReadBooks();
        if (null!= books){
            for (Book b: books){
                if(b.getId() == book.getId()){
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_BOOKS);
                        editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromWantToReadBooks (Book book){
        ArrayList<Book> books = getWantToReadBooks();
        if (null!= books){
            for (Book b: books){
                if(b.getId() == book.getId()){
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_BOOKS);
                        editor.putString(WANT_TO_READ_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromFavouriteBooks (Book book){
        ArrayList<Book> books = getFavouriteBooks();
        if (null!= books){
            for (Book b: books){
                if(b.getId() == book.getId()){
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVOURITE_BOOKS);
                        editor.putString(FAVOURITE_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromCurrentlyReading (Book book){
        ArrayList<Book> books = getCurrentlyReadBooks();
        if (null!= books){
            for (Book b: books){
                if(b.getId() == book.getId()){
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS);
                        editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
