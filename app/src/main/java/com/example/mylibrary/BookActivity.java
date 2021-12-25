package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    public static final String BOOK_ID_KEY= "bookId";
    private TextView txtBookName, txtAuthor, txtPages, txtDescription;
    private Button btnAddToFavouriteRead, btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavourite;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        initViews();

        //String longDescription= "When it comes to relationships, Colin Singleton's type happens to be girls named Katherine. And when it comes to girls named Katherine, Colin is always getting dumped. Nineteen times, to be exact.";
        //Book book = new Book(1, "An abundance of Katherines", "John Green", 256,"https://images-na.ssl-images-amazon.com/images/I/71I6ooyL2LL.jpg", "a child prodigy and a wannabe genius, who only seems to want to date girls named Katherine",
        //        "long description here");

        Intent intent =getIntent();
        if (null!= intent){
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1){
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (null!= incomingBook){
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavouriteBooks(incomingBook);
                }

            }
        }

    }

    private void handleCurrentlyReadingBooks(final Book book){
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadBooks();
        boolean existInCurrentlyReadingBooks = false;
        for (Book b: currentlyReadingBooks){
            if (b.getId()==book.getId()){
                existInCurrentlyReadingBooks = true;
            }
        }
        if (existInCurrentlyReadingBooks){
            btnAddToCurrentlyReading.setEnabled(false); //disable button if the book already exists in the list
        }else{
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToCurrentlyReading(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, please try again.", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
    }

    private void handleFavouriteBooks(final Book book){
        ArrayList<Book> favouriteBooks = Utils.getInstance(this).getFavouriteBooks();
        boolean existInFavouriteBooks = false;
        for (Book b: favouriteBooks){
            if (b.getId()==book.getId()){
                existInFavouriteBooks = true;
            }
        }
        if (existInFavouriteBooks){
            btnAddToFavourite.setEnabled(false); //disable button if the book already exists in the list
        }else{
            btnAddToFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToFavourite(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, FavouriteActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, please try again.", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
    }

    private void handleWantToReadBooks(final Book book){
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();
        boolean existInWantToReadBooks = false;
        for (Book b: wantToReadBooks){
            if (b.getId()==book.getId()){
                existInWantToReadBooks = true;
            }
        }
        if (existInWantToReadBooks){
            btnAddToFavouriteRead.setEnabled(false); //disable button if the book already exists in the list
        }else{
            btnAddToFavouriteRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToWantToRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, please try again.", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }

    }

    private void handleAlreadyRead(Book book){
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();
        boolean existInAlreadyReadBooks = false;
        for (Book b: alreadyReadBooks){
            if (b.getId()==book.getId()){
                existInAlreadyReadBooks = true;
            }
        }
        if (existInAlreadyReadBooks){
            btnAddToAlreadyRead.setEnabled(false); //disable button if the book already exists in the list
        }else{
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, please try again.", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }

    }
    private void setData(Book book){
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap().load(book.getImgUrl())
                .into(bookImage);

    }

    private void initViews() {
        txtAuthor = findViewById(R.id.textAuthorName);
        txtBookName = findViewById(R.id.txtBookTitle);
        txtPages = findViewById(R.id.txtPages);
        txtDescription =findViewById(R.id.txtDescrption);

        btnAddToAlreadyRead=findViewById(R.id.btnAddToAlreadyRead);
        btnAddToCurrentlyReading=findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavourite=findViewById(R.id.btnAddToFavourites);
        btnAddToFavouriteRead =findViewById(R.id.btnAddToWishlist);

        bookImage= findViewById(R.id.imageBook);
    }

}