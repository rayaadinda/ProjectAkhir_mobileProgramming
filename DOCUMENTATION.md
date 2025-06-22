# Outdoor Gear Store Mobile Application

## Overview
This Android mobile application is designed for an outdoor gear store, allowing users to manage inventory, view products, and utilize various device features such as sensors, speech recognition, and location services. The application provides a comprehensive platform for both customers and store administrators to interact with outdoor gear products.

## Features

### User Authentication
- **Login**: Secure login system with authentication verification
- **Registration**: User account creation with validation for email and password
- **Password Reset**: Functionality to reset forgotten passwords

### Dashboard
The dashboard serves as the central hub of the application, providing:
- Product overview with summary statistics
- Quick access to all features through visual cards
- Navigation drawer for menu access
- Display of featured products

### Product Management
- **View Products**: Browse the complete product catalog
- **Search Products**: Filter products by keywords
- **Add Products**: Add new products with details and images
- **Edit/Delete Products**: Modify existing product information
- **Product Categories**: Organized by categories such as Jackets, Footwear, Backpacks

### Inventory Management
- Track product stock levels
- Monitor inventory value
- Manage product details (name, price, quantity, category, image)

### Hardware Integration
- **Sensors**: Access and display device sensor data (accelerometer, proximity, gyroscope)
- **Camera**: Preview camera for product images
- **Maps**: Location services using OpenStreetMap integration
- **Speech Recognition**: Voice-to-text functionality

## Technical Features

### Database
The application uses SQLite for local data storage:
- Product information (name, category, price, quantity, image)
- Sample data preloaded for demonstration
- CRUD operations (Create, Read, Update, Delete)

### UI/UX Design
- Modern, clean interface using Material Design principles
- Responsive layouts for different screen sizes
- Custom components and styles with consistent theme
- Intuitive navigation with bottom navigation and drawer

### Components

#### Activities
1. **MainActivity**: Login screen and entry point
2. **RegisterActivity**: User registration
3. **Dashboard**: Main application interface
4. **InputData**: Product data entry form
5. **LihatDataActivity**: Product listing and search
6. **MapActivity**: Location services integration
7. **SensorActivity**: Device sensor display
8. **SpeechRecognizer**: Voice recognition functionality

#### Helpers
- **DatabaseHelper**: SQLite database management
- **PakaianAdapter**: Product display adapter for lists

## Installation & Setup
1. Clone the repository
2. Open the project in Android Studio
3. Build the project using Gradle
4. Run on an emulator or physical device with Android API level supported

## Usage Guide

### Login
- Default credentials: username "admin" and password "1234"
- Register new account through the registration screen

### Adding Products
1. Navigate to "Add Product" from the dashboard
2. Complete the product details form
3. Add an image (optional)
4. Save the product

### Viewing Products
1. Select "View Products" from the dashboard
2. Browse the list of products
3. Use the search function to filter by name

### Using Special Features
- **Sensors**: Access sensor data from the dashboard features card
- **Maps**: View location information from the maps feature
- **Speech Recognition**: Convert speech to text using the speech feature

## System Requirements
- Android 5.0 (API level 21) or higher
- Camera permission for product images
- Location permission for maps
- Microphone permission for speech recognition
- Storage permission for image saving

## Development
- Developed by: Raya Adinda Jayadi Ahmad
- Framework: Native Android (Java)
- IDE: Android Studio

## Libraries Used
- Material Components for Android
- Glide for image loading
- OSMDroid for map integration

## Future Enhancements
- Cloud synchronization for inventory
- User roles and permissions
- Barcode scanning for quick product lookup
- Sales and analytics reporting
- Customer management 