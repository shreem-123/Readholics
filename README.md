# Readholics
A Library Management System supported with recommendation system for users

Key Functionalities:-
1.	Maintaining membership data
2.	Maintaining book availability status
3.	Maintaining borrower info
4.	Maintaining/updating metadata of the books borrowed
5.	Listing top 'n' recommended books of each genre , based on the mostly borrowed by the users of the library in a particular genre

So, this is a library management application that enables one to manage library as admin but also allows users to get recommendations as per genre of their choice. this project solely uses SpringBoot and leverages it various functionalities by integrating dependencies. Like mysql-connector, Spring Web, Spring Data JPA , JDBC API enables one to connect to database and JPA repository provides several predefined functions to fetch data.

Also makes use of Postman & MySQL.

![WhatsApp Image 2024-06-16 at 10 27 45_c1ac0c26](https://github.com/shreem-123/Readholics/assets/96364929/9f458276-cfb6-430b-96d1-36c3a36d0ada)

 Book Entity 
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @NotBlank(message = "Title is mandatory!")
    private String title;

    @Getter
    @Setter
    @NotBlank(message = "Author is mandatory!")
    private String author;

    @Getter
    @Setter
    @NotNull(message = "Genre Id is mandatory!")
    private int genreId;

    @Setter
    @Getter
    @Min(value=1,message="At least one copy required!")
    private int totalCopies=1;

    @Getter
    @Setter
    @Min(value=0,message="Available copies can't be negative!")
    private int availableCopies=1;

    @Getter
    @Setter
    @Min(value=0,message="Borrow Count can't be negative!")
    private int borrowCount;

    public Book() {
    }

    public Book(int id, String title, String author, int genreId) {
        this.title = title;
        this.author = author;
        this.genreId = genreId;
    }

    public int getBookId() {
        return id;
    }

    @Min(value = 0, message = "Available copies can't be negative!")
    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(@Min(value = 0, message = "Available copies can't be negative!") int availableCopies) {
        this.availableCopies = availableCopies;
    }
}
 Book Register Entity 
@Entity
@Table(name="Book_Register")
public class BookRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="book_id", foreignKey = @ForeignKey(name="bookId"))
    @NotNull(message="Book Id required")
    private Book book;


    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(name="userId"))
    @NotNull(message="User Id required")
    private User user;

    @Getter
    @Setter
    @NotNull(message ="Borrow Date is mandatory!")
    private Date borrowDate;

    @Setter
    @Getter
    private Date returnDate;

    public int getRecordId() {
        return id;
    }

    public BookRegister(int id, Book book, User user, Date borrowDate, Date returnDate) {
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        if (returnDate != null) {
            this.returnDate = returnDate;
        } else {
            // Calculate returnDate based on borrowDate + 2 months
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(borrowDate);
            calendar.add(Calendar.MONTH, 2);
            this.returnDate = calendar.getTime();
        }
    }

    public BookRegister() {
    }

}
 Genre Entity 
@Entity
@Table(name = "Genre")
public class Genre {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @NotBlank(message ="Genre Name required!")
    @Column(name = "genre_name")
    private String genreName;

    public Genre() {
    }

    public Genre(int genreId, String name) {
        this.genreName = name;
    }

    public void setName(String name) {
        this.genreName = name;
    }
}
 User Entity 
@Entity
@Table(name="User")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Getter
    @NotBlank(message = "User name required!")
    private String userName;

    @Setter
    @Getter
    private String status="Active";

    @Setter
    @Getter
    private Date renewalDate= new Date(2025,6,1);


    public User(int userId,String userName, String status, Date renewalDate) {
        this.userName = userName;
        this.status = status;
        if(renewalDate != null) {
            this.renewalDate = renewalDate;
        }
        else{
            Calendar current_date= Calendar.getInstance();
            current_date.add(Calendar.YEAR,1);
            this.renewalDate= current_date.getTime();
        }
    }

    public User() {
        this.userName = "DefaultUserName";
        this.status = "Active";
    }
}
API calls used in the application are:
•	Add a book
Allows admin to add book or multiple books to the database
API url: {{host}}:{{port}}/library/books/add
Output:
 ![image](https://github.com/shreem-123/Readholics/assets/96364929/a74c6333-9fec-4de4-9c46-cd4d3c680155)

•	Borrow a book
Allows admin to add a borrow record to the database
API url: {{host}}:{{port}}/library/books/borrow?bookId=171&userId=5
Incase the user is inactive it returns “Inactive User”.
Output:
![WhatsApp Image 2024-06-16 at 10 27 32_1c7f4934](https://github.com/shreem-123/Readholics/assets/96364929/d5a85504-098a-4cc4-b6a3-ed75b2f7aaac)


API url: {{host}}:{{port}}/library/books/borrow?bookId=171&userId=51
Output:
 ![WhatsApp Image 2024-06-16 at 10 29 20_e9c669fe](https://github.com/shreem-123/Readholics/assets/96364929/37e24edc-d16d-4df0-aac0-64c579d3ce33)


•	Return a book
Allows admin to enter a return record to the database
API url: {{host}}:{{port}}/library/books/return?bookId=113&userId=15
Incase recurrent entries are made, error message “Cannot return more Copies than total available.” is displayed”
Output:
 ![WhatsApp Image 2024-06-16 at 10 30 00_d9ef8637](https://github.com/shreem-123/Readholics/assets/96364929/56a1a3c1-93fd-4834-ac4c-6c388e4b1ee1)


•	Add a genre
Allows admin to add a genre to the genre database
API url: {{host}}:{{port}}/library/genres/add
Output:
 ![WhatsApp Image 2024-06-16 at 10 30 16_758ed65e](https://github.com/shreem-123/Readholics/assets/96364929/d4bf65c0-521b-465a-a86c-5ec4ab41cd97)

•	Get genre details
Allows admin to view genre details
API url: {{host}}:{{port}}/library/genres/2
Output:
 ![WhatsApp Image 2024-06-16 at 10 30 27_e4e6eb89](https://github.com/shreem-123/Readholics/assets/96364929/fbee762c-ff8c-44f6-9673-c951dd8dbf6b)

•	Add a user
Allows admin to add a user to the database
API url: {{host}}:{{port}}/library/users/add
Output:
 ![WhatsApp Image 2024-06-16 at 10 30 39_8e9ff1db](https://github.com/shreem-123/Readholics/assets/96364929/7affbf03-5d24-4386-9771-ce2bbdf02d07)

•	Get user details
Allows admin to fetch a users detail from the database
API url: {{host}}:{{port}}/library/users/120
Output:
 ![WhatsApp Image 2024-06-16 at 10 30 52_9227f91a](https://github.com/shreem-123/Readholics/assets/96364929/299b8bda-c0ca-450a-abd9-2b7fa303a974)

•	Get top recommendations 
Allows users to get top recommendations as per borrowing of books by readers of the library
 Query 
SELECT b.title, b.author,COUNT(br.id) borrow_count,(total_copies-COUNT(br.id)) available_copies
FROM book_register br
JOIN book b ON br.book_id = http://b.id 
JOIN genre g ON b.genre_id = g.id
WHERE g.genre_name = "Fictional"
GROUP BY b.title, b.author
ORDER BY COUNT(br.id) DESC;
API url: {{host}}:{{port}}/library/books/topRecommendations?genreName=Comic&recCount=8
Output:
 ![WhatsApp Image 2024-06-16 at 10 31 04_82e72b14](https://github.com/shreem-123/Readholics/assets/96364929/0dd61948-0bb7-4f83-9cc1-20e246382aa6)

 Query Output: 
[
{
"title": "V for Vendetta",
"author": "Alan Moore",
"borrowCount": 2,
"availableCount": 48
},
{
"title": "Watchmen",
"author": "Alan Moore",
"borrowCount": 2,
"availableCount": 43
},
{
"title": "Maus",
"author": "Art Spiegelman",
"borrowCount": 2,
"availableCount": 45
},
{
"title": "Bone",
"author": "Jeff Smith",
"borrowCount": 2,
"availableCount": 39
},
{
"title": "Hellboy",
"author": "Mike Mignola",
"borrowCount": 2,
"availableCount": 37
},
{
"title": "Akira",
"author": "Katsuhiro Otomo",
"borrowCount": 2,
"availableCount": 35
},
{
"title": "Ghost in the Shell",
"author": "Masamune Shirow",
"borrowCount": 2,
"availableCount": 41
},
{
"title": "One Piece",
"author": "Eiichiro Oda",
"borrowCount": 2,
"availableCount": 48
}
]

