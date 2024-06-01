# Tunehub
### TuneHub: Web Development Project Summary

TuneHub is a music streaming web application built with Java and Spring Boot, utilizing the Model-View-Controller (MVC) architecture for a structured and efficient codebase. It features secure user authentication, a comprehensive music library, and playlist management, all backed by a MySQL database. The responsive design ensures an optimal experience across various devices. By combining robust backend technology with a user-friendly interface, TuneHub offers users an engaging and seamless music listening experience.

## Requirements

For building and running the application you need:
- [Spring Tools] (<a href="https://cdn.spring.io/spring-tools/release/STS4/4.22.1.RELEASE/dist/e4.31/spring-tool-suite-4-4.22.1.RELEASE-e4.31.0-win32.win32.x86_64.self-extracting.jar">Here</a>)
- [My SQL] (<a href="https://dev.mysql.com/downloads/file/?id=526927">Here</a>)

<br>

### It follow the MVC architecture flow -(as show in below picture)
<br>
<img align="center" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/mvc.png">
<br>
<br>

### Structure that follow:
<br>
<img align="center" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/structure.png">
<br>

## Here are Some pictures of websites

<h4> Home page </h4>
<img align="center" width="500" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/home.png">
<br>

###

<div>
  <h4 align="center"> LogIN and Registration page</h4>
  <img width="500" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/login.png"/>
  <img width="500" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/registration.png"/>
  <br>
</div>


### We have three option while login 

* As Admin
* As client (Non premium)
* As client (Premium)

<h1 align="center"> ->As admin<- </h1>
<img align="left" width="600" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/adminhome.png"/>

### We got four feature

- Add New Song
- View All Songs
- Create Playlist
- View Playlist

  <br>
  <br>

### Thsoe are the image
* Add new songs
  <br>
  <img width="600" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/addsong.png"/>
  <br>
* View All Songs
  <br>
  <img width="600" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/dispSong.png"/>
  <br>
* Create Playlist
  <br>
  <img width="600" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/crtPlaylist.png"/>
  <br>
* View Playlist
  <br>
  <img width="600" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/dispPlaylist.png"/>
  <br>
  
<h1 align="center"> ->As client (Non premium)<- </h1>
<img width="600" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/nonprem.png"/>

### <p> Note: </p>
To make this premium you have to buy the package for that we use Razorpay Gateway Integration with SpringBoot
 * Payment page
   <br>
   <img width="600" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/payment.png"/>
   <br>
 * Payment
   <br>
   <img width="600" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/razpay.png"/>
   <br>

<h1 align="center"> ->As client (Premium)<- </h1>
  
### <p> Note: </p>
Onces we paid, same client can able to access music
<br>
<img width="600" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/prem.png"/>

<br>

# Backend

We used My SQL, This is our database Name : <b> tunehub_db </b>

Table which are created for this operations are :-
<br>

```shell
SHOW TABLES ;
```
<br>
<img width="200" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/tabledb.png"/>
<br>

```shell
SELET * FROM USERS ;
```
<br>
Actual users <br>
<img width="800" src="https://github.com/shyam0880/Tunehub/blob/main/Readme%20image/usersdb.png"/>





