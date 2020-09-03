package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "data2.db";
    private static final String DATABASE_PATH = "/data/data/com.example.project/databases/";
    private final Context context;
    SQLiteDatabase db;

    // Table Names
    public static final String TABLE_SALES = "sales";
    public static final String TABLE_USER = "user";
    public static final String TABLE_CUSTOMER = "customer";
    public static final String TABLE_ADMIN = "admin";
    public static final String TABLE_PRODUCT = "product";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_CART = "cart";

    // Table Create Statements-total_price DOUBLE, quantity INTEGER
    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS product(product_id INTEGER PRIMARY KEY AUTOINCREMENT, product_name VARCHAR, product_desc VARCHAR, product_price VARCHAR, product_pic BLOB, category_name VARCHAR);";
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE IF NOT EXISTS category(category_id INTEGER PRIMARY KEY AUTOINCREMENT, category_name VARCHAR);";
    private static final String CREATE_TABLE_CART = "CREATE TABLE IF NOT EXISTS cart(cart_id INTEGER PRIMARY KEY AUTOINCREMENT, product_name VARCHAR, product_price VARCHAR, product_pic BLOB, username VARCHAR);";
    private static final String CREATE_TABLE_SALES = "CREATE TABLE IF NOT EXISTS sale(sale_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, username VARCHAR, price DOUBLE, sale_date VARCHAR);";
    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE IF NOT EXISTS customer(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, username VARCHAR, password VARCHAR, phone NUMBER, email VARCHAR);";
    private static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user(username VARCHAR PRIMARY KEY, password VARCHAR, userType VARCHAR);";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        createDb();
    }

    public void createDb() {
        boolean dbExit = checkExist();
        if (!dbExit) {
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private boolean checkExist() {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            String path = DATABASE_PATH + DATABASE_NAME;
            sqLiteDatabase = sqLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception ex) {

        }
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
            return true;
        }
        return false;
    }

    private void copyDatabase() {
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);
            String outFileName = DATABASE_PATH + DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private SQLiteDatabase openDatabase() {
        String path = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public boolean checkUserExist(String username, String password) {
        String[] columns = {"username"};
        db = openDatabase();
        String selection = "username=? and password=?";

        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_CUSTOMER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        close();
        if (count > 0) {
            return true;

        } else {
            return false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SALES);
        db.execSQL(CREATE_TABLE_CUSTOMER);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        // create new tables
        onCreate(db);
    }



    //insert new Product
    public boolean insertProduct(String productName, String productDesc, String productPrice, byte[] photo, String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("product_name", productName);
        contentValues.put("product_desc", productDesc);
        contentValues.put("product_price", productPrice);
        contentValues.put("product_pic", photo);
        contentValues.put("category_name", categoryName);

        long result = db.insert(TABLE_PRODUCT, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //get all product to each category
    public Cursor getAllProduct(String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT
                + " WHERE TRIM(category_name) = '" + categoryName.trim() + "'", null);
        return res;
    }//end of getAllProduct()

    public Customer getCustomer(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CUSTOMER
                + " WHERE TRIM(username) = '" + name.trim() + "'", null);
        Customer customer = new Customer();
        if (cursor.moveToFirst()) {
            customer.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("userid"))));
            customer.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            customer.setName(cursor.getString(cursor.getColumnIndex("name")));
            customer.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            customer.setPhoneNumber(cursor.getInt(cursor.getColumnIndex("phone")));
            customer.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        }
        cursor.close();
        db.close();
        return customer;
    }//end of getCustomer()

    public void updateCustomer(String name, String username, String password, int phone, String email, int id) {
        SQLiteDatabase db = getWritableDatabase();

        String sql = "UPDATE customer SET name = ?, username = ?, password = ?, phone = ?, email = ? WHERE userid = " + id;
        SQLiteStatement statement = db.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, username);
        statement.bindString(3, password);
        statement.bindDouble(4, (double) phone);
        statement.bindString(5, email);

        statement.execute();
        db.close();
    }

    public int GetUserID(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select userid from customer WHERE username"+" LIKE '%"+username+"%'";
        Cursor c = db.rawQuery(query, null);
        if(c.getCount()>0)
            return c.getInt(0);
        else
            return 0;
    }

//    //todo test
//    public void insertCart(int user,int product) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("product_id", product);
//        contentValues.put("userid",user);
//        db.insert(TABLE_CART, null, contentValues);
//    }
//    public ArrayList<Product> getCart() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ArrayList<Product> products=new ArrayList<>();
//        Cursor cursor = db.rawQuery("select * from " + TABLE_CART , null);
//        while (cursor.moveToNext()) {
//            products.add(getAllProduct(cursor.getString(cursor.getColumnIndex("product_id"))));
//        }
//        cursor.close();
//        return products;
//    }


}//end of class helper