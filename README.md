# My-Store
<p align="center">
  <img width="250" height="200" src="https://user-images.githubusercontent.com/103619788/215250340-60fb26a6-4ad8-4345-aa00-dd10724f916b.jpg">
</p>


My Store is a spring boot application developed to work as a online shopping application. This application provides REST API for an online shopping application. This API performs all the fundamental CRUD operations of any Real life online shopping application with user validation at every step.

# Modules 

- Admin Module  
- Category Module
- Product Module
- Customer Module
- Address Module
- Cart Module
- Orders Module

# Features 

### Admin Features 

- Sign-Up Admin Handler (http://localhost:8088/adminController/adminSignUp)
- Log-in Admin Handler (http://localhost:8088/adminController/loginAdmin)
- Log-out Admin Handler (http://localhost:8088/adminController/logoutAdmin?key={KEY})
- Find All Admins Handler (http://localhost:8088/adminController/admins)
- Find Admin By Admin Id Handler (http://localhost:8088/adminController/adminsByAdminId?adminId={adminId})
- Find Admin By User-Name Handler (http://localhost:8088/adminController/adminsByUserName?adminUserName={adminUserName})
- Update Admin Details Handler (http://localhost:8088/adminController/updateAdmin?key={KEY})
- Delete Admin Handler (http://localhost:8088/adminController/deleteAdmins?key={KEY})

![01](https://user-images.githubusercontent.com/103619788/220114469-601fbb07-598b-44ed-865d-d1becb749b9c.jpg)

### Category Features 

- Add Category Handler (http://localhost:8088/categoryController/addCategory?key={KEY})
- Find Category By Category Name Handler (http://localhost:8088/categoryController/categoryByCategoryName?categoryName={categoryName})
- Find All Categorys Handler(http://localhost:8088/categoryController/categorys)
- Update Category Details Handler(http://localhost:8088/categoryController/updateCategory?key={KEY})
- Delete Category Handler(http://localhost:8088/categoryController/deleteCategory?categoryName={categoryName}&key={KEY})

![03](https://user-images.githubusercontent.com/103619788/220283745-11c5ca3a-1398-469b-be67-07a713ef6d97.jpg)

### Product Features 

- Add Product Handler (http://localhost:8088/productController/addProduct?categoryName={categoryName}&key={KEY})
- Find Product By Category Name Handler(http://localhost:8088/productController/productByCategoryName?categoryName={categoryName})
- Find Product By Product Name Handler(http://localhost:8088/productController/productByProductName?productName={productName})
- Find All Product Handler(http://localhost:8088/productController/Products)
- Update Product Details Handler(http://localhost:8088/productController/updateProduct?key={KEY})
- Delete Product Handler(http://localhost:8088/productController/deleteProduct?productId={productId}&categoryName={categoryName}&key={KEY})

![04](https://user-images.githubusercontent.com/103619788/220287227-d7603a48-2d88-4c99-954c-0d8144457b03.jpg)

# Technology and Tools used 

- Java
- MySQL
- Spring Boot
- Spring Data JPA
- Hibernate
- lombok
- Swagger
- Maven
- Git & GitHub
- Spring tool suite

# Entity Relationship Diagram (ERD)
![123](https://user-images.githubusercontent.com/103619788/219714882-03e5f097-63a4-4ab8-9d8b-ce2842e6eff2.jpg)
