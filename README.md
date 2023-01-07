# Products-Suppliers-Application

This repository contains 2 applications :
- **`product-app`**
- **`supplier-app`**

### Securing The applications using Keycloak :

Keycloak is an open source **identity and access management** solution.

###  a. Setting Up a Keycloak Server : 

To be able to use it to secure our application, we must configure it.
To do so:

- we created a realm called **"E-commerce-realm"**, and added the clients which are the applications to secure.

![image](https://user-images.githubusercontent.com/84817425/211116241-783c11d8-1865-4a65-8b80-9cf5f9fc03d7.png)

- we added the different users.

![image](https://user-images.githubusercontent.com/84817425/211116294-0adc9fbb-738b-4e50-b694-abc0008595f9.png)

- we added the different roles that we need and assign them to each corresponding user.

![image](https://user-images.githubusercontent.com/84817425/211116315-68c3fa61-0ccf-44f7-b29d-197612f0364d.png)

#### Testing the different authentication modes using Postman
##### Using the access token :
To do so:

- We made a POST request to the token endpoint of the Keycloak server. <http://localhost:8080/realms/E-commerce-realm/protocol/openid-connect/token>
- In the "Body" tab, we selected the "x-www-form-urlencoded" option, and then we added the following key-value pairs to the request:
   - "username".
   - "password".
   - "grant_type": password
   - "client_id": (The client ID of your application)

![image](https://user-images.githubusercontent.com/84817425/211116450-6c32c55d-f2a2-4ad0-828c-7ca12290c87d.png)

##### Using the refresh token :
To do so:

1. We made a POST request to the token endpoint of the Keycloak server. <http://localhost:8080/realms/E-commerce-realm/protocol/openid-connect/token>
2. In the "Body" tab, we selected the "x-www-form-urlencoded" option, and then we added the following key-value pairs to the request:
   - "username".
   - "password".
   - "grant_type": refresh_token
   - "refresh_token": (the refresh token to refresh/retrieve a new the access token)

![image](https://user-images.githubusercontent.com/84817425/211116526-a6e93c8f-5cd7-41a0-a687-a91ae7e329f2.png)

### b. Securing the applications

To secure each application we took the following steps:
1. we added the following dependencies in **`pom.xml`**
```xml		
<dependency> 
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-spring-boot-starter</artifactId>
    <version>20.0.1</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>		
    <version>2.7.7</version>
</dependency>
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity5</artifactId>
</dependency>
```
2. we added keycloak configuration in **`application.properties`** :
```
keycloak.realm=E-commerce-realm
keycloak.resource=products-app
keycloak.bearer-only=true
keycloak.auth-server-url=http://localhost:8080
keycloak.ssl-required=none
```
3. We then added configuration classes inside of the **`security`** package.
  - We created the **`KeycloakAdapterConfig`** class to easily integrate Keycloak with our application.
 ```java 
 @Configuration
public class KeycloakAdapterConfig {
    @Bean
    KeycloakSpringBootConfigResolver springBootConfigResolver(){
        return new KeycloakSpringBootConfigResolver();
    }
}
 ```
 - We created **`KeycloakSecurityConfig`** class to configure the security settings for our application that is protected by Keycloak. In this class we did override the configure(HttpSecurity) method to specify the security constraints for our application. 
For example: 
 ```java
 @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests().antMatchers("/products/**","/suppliers/**").authenticated();
    }
 ```
The example above shows that the ANY user that wants to access the endpoints **`/products/**`** & **`/suppliers/**`** must be authenticated first.
 - Before authentication:
 
 ![image](https://user-images.githubusercontent.com/84817425/211117402-9544222c-0ca7-4039-b8f0-6d15718e5028.png)

- After authentication:

![image](https://user-images.githubusercontent.com/84817425/211117483-ca9079d7-a645-4bfb-ae30-d869850392ed.png)
