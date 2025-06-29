# 📚 Kütüphane Yönetim Sistemi

Modern Java teknolojileri kullanılarak geliştirilmiş kapsamlı bir kütüphane yönetim sistemi.

## 🚀 Özellikler

- **Kitap Yönetimi**: Ekleme, düzenleme, silme, arama
- **Üye Yönetimi**: Kayıt, profil, ödünç alma geçmişi
- **Ödünç Alma Sistemi**: Kitap ödünç alma/iade, gecikme takibi
- **Rezervasyon Sistemi**: Kitap rezervasyonu ve durum takibi
- **İstatistikler**: Popüler kitaplar, kategori bazlı raporlar
- **Modern Web Arayüzü**: Responsive tasarım, Bootstrap 5

## 🛠️ Teknolojiler

### Backend
- **Java 8+** - Modern Java özellikleri
- **Spring Boot 3.x** - Ana framework
- **Spring Data JPA** - Veritabanı işlemleri
- **H2 Database** - In-memory veritabanı
- **Maven** - Build tool
- **Lombok** - Code generation

### Frontend
- **Thymeleaf** - Template engine
- **Bootstrap 5** - CSS framework
- **Font Awesome** - İkonlar
- **JavaScript** - Client-side interaktivite

## 📋 Gereksinimler

- Java 8 veya üzeri
- Maven 3.6+
- Git

## ⚡ Hızlı Başlangıç

### 1. Projeyi Klonlayın
```bash
git clone https://github.com/[kullanici-adi]/library-management-system.git
cd library-management-system
```

### 2. Bağımlılıkları Yükleyin
```bash
mvn clean install
```

### 3. Uygulamayı Çalıştırın
```bash
mvn spring-boot:run
```

### 4. Erişim
- **Web Arayüzü**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
- **API Endpoints**: http://localhost:8080/api/books

## 🗄️ Veritabanı

Uygulama başlatıldığında otomatik olarak örnek veriler yüklenir:

- **Kategoriler**: Roman, Bilim, Tarih, Teknoloji
- **Yazarlar**: Örnek yazar bilgileri
- **Kitaplar**: Çeşitli kategorilerde örnek kitaplar
- **Üyeler**: Test üyeleri
- **Ödünç Kayıtları**: Örnek ödünç alma kayıtları

## 📖 API Dokümantasyonu

### Kitap Endpoints
```
GET    /api/books              # Tüm kitapları listele
POST   /api/books              # Yeni kitap ekle
GET    /api/books/{id}         # Kitap detayı
PUT    /api/books/{id}         # Kitap güncelle
DELETE /api/books/{id}         # Kitap sil
GET    /api/books/search       # Kitap arama
```

### İstatistik Endpoints
```
GET    /api/statistics         # Genel istatistikler
GET    /api/statistics/books   # Kitap istatistikleri
GET    /api/statistics/members # Üye istatistikleri
```

## 🏗️ Proje Yapısı

```
src/main/java/com/library/
├── config/                 # Konfigürasyon sınıfları
├── controller/            # Web controller'lar
├── entity/               # JPA entity'leri
├── repository/          # Data access layer
├── service/            # Business logic layer
└── LibraryManagementApplication.java
```

## 🚀 Deployment

### Local Development
```bash
mvn spring-boot:run
```

### Production Build
```bash
mvn clean package
java -jar target/library-management-system-1.0.0.jar
```

### Docker (Opsiyonel)
```bash
docker build -t library-management-system .
docker run -p 8080:8080 library-management-system
```

## 📊 Kullanılan Java Konuları

### ✅ Java 8 Functional Programming
- Lambda Expressions
- Stream API
- Optional
- Method References

### ✅ Inheritance & Polymorphism
- BaseEntity inheritance
- Generic programming
- Interface implementations

### ✅ Collections Framework
- List, Set, Map kullanımı
- Stream operations
- Custom collections

### ✅ Multithreading
- @Async annotations
- CompletableFuture
- Thread-safe operations

### ✅ Database Connection
- JPA/Hibernate ORM
- Repository pattern
- Transaction management

### ✅ J2EE & Web Programming
- Spring Boot framework
- RESTful web services
- MVC pattern
- Template engines

### ✅ Popular Frameworks
- Spring Boot
- Spring Data JPA
- Maven
- Lombok

## 🎯 Özellikler Detayı

### Kitap Yönetimi
- ✅ CRUD operasyonları
- ✅ Gelişmiş arama ve filtreleme
- ✅ Kategori ve yazar bazlı gruplama
- ✅ Stok takibi
- ✅ ISBN doğrulama

### Üye Yönetimi
- ✅ Üye kayıt sistemi
- ✅ Profil yönetimi
- ✅ Ödünç alma geçmişi
- ✅ Aktif rezervasyonlar

### Ödünç Alma Sistemi
- ✅ Kitap ödünç alma/iade
- ✅ Gecikme takibi
- ✅ Otomatik bildirimler
- ✅ Ödünç alma limitleri

### Rezervasyon Sistemi
- ✅ Kitap rezervasyonu
- ✅ Rezervasyon durumu takibi
- ✅ Otomatik rezervasyon iptali
- ✅ Rezervasyon geçmişi

### İstatistikler ve Raporlama
- ✅ Popüler kitaplar
- ✅ Kategori bazlı istatistikler
- ✅ Üye aktivite raporları
- ✅ Ödünç alma trendleri

## 🎨 Frontend Özellikleri

- **Responsive Design**: Mobil uyumlu tasarım
- **Modern UI**: Bootstrap 5 ile modern görünüm
- **Interactive Elements**: JavaScript ile dinamik özellikler
- **User-Friendly**: Kullanıcı dostu navigasyon
- **Icons**: Font Awesome ikonları

## 🔧 Konfigürasyon

### application.properties
```properties
# Database
spring.datasource.url=jdbc:h2:mem:librarydb
spring.datasource.driverClassName=org.h2.Driver

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true

# Server
server.port=8080
```

## 🧪 Test

```bash
# Unit tests
mvn test

# Integration tests
mvn verify

# Coverage report
mvn jacoco:report
```

## 📝 Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

## 🤝 Katkıda Bulunma

1. Fork edin
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Commit edin (`git commit -m 'Add amazing feature'`)
4. Push edin (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 📞 İletişim

- **Geliştirici**: [Öğrenci Adı]
- **Email**: [email@example.com]
- **GitHub**: [github.com/kullanici-adi]

## 🙏 Teşekkürler

- Spring Boot ekibine
- Bootstrap ekibine
- Font Awesome ekibine
- Tüm open source topluluğuna

---

**Versiyon**: 1.0.0  
**Son Güncelleme**: Haziran 2024  
**Durum**: ✅ Tamamlandı 