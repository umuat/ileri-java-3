# Kütüphane Yönetim Sistemi - Proje Bilgilendirme Notu

## Proje Hakkında
Bu proje, Java 8+ ve Spring Boot kullanılarak geliştirilmiş modern bir kütüphane yönetim sistemidir. Proje, öğretim görevlisinin belirttiği Java konularını kapsamlı bir şekilde ele almaktadır.

## Kullanılan Java Konuları

### 1. Java 8 Functional Programming
- **Lambda Expressions**: Stream API'lerinde ve servis katmanında yoğun kullanım
- **Stream API**: Kitap arama, filtreleme ve istatistik hesaplamalarında
- **Optional**: Null safety için repository katmanında
- **Method References**: Servis implementasyonlarında

### 2. Inheritance
- **BaseEntity**: Tüm entity'ler için ortak alanlar (id, createdDate, updatedDate)
- **GenericRepository & GenericService**: Generic programming ile kod tekrarını önleme
- **Entity Hierarchy**: Book, Author, Member, Category arasındaki ilişkiler

### 3. Collections
- **List, Set, Map**: Repository ve servis katmanlarında veri yapıları
- **Stream Operations**: Koleksiyon işlemleri için functional programming
- **Custom Collections**: Özel veri yapıları ve algoritmalar

### 4. Multithreading
- **@Async**: Asenkron metodlar ile performans optimizasyonu
- **CompletableFuture**: Asenkron işlemler için modern Java API
- **Thread Safety**: Repository ve servis katmanlarında thread-safe operasyonlar

### 5. Database Connection
- **JPA/Hibernate**: ORM framework ile veritabanı işlemleri
- **H2 Database**: In-memory veritabanı (production'da PostgreSQL kullanılabilir)
- **Repository Pattern**: Data access layer için modern yaklaşım

### 6. J2EE & Web Programming
- **Spring Boot**: Modern Java web framework
- **REST API**: RESTful web servisleri
- **Thymeleaf**: Server-side template engine
- **Spring MVC**: Web controller'lar

### 7. Popular Frameworks
- **Spring Boot**: Ana framework
- **Spring Data JPA**: Veritabanı işlemleri
- **Spring Web**: Web katmanı
- **Maven**: Dependency management ve build tool
- **Lombok**: Boilerplate code azaltma
- **Bootstrap**: Frontend UI framework

## Proje Yapısı

```
src/main/java/com/library/
├── config/                 # Konfigürasyon sınıfları
│   ├── DataLoader.java    # Başlangıç verisi yükleme
│   └── JpaConfig.java     # JPA konfigürasyonu
├── controller/            # Web controller'lar
│   ├── BookController.java # REST API controller
│   └── WebController.java  # Thymeleaf controller
├── entity/               # JPA entity'leri
│   ├── BaseEntity.java   # Temel entity sınıfı
│   ├── Book.java         # Kitap entity'si
│   ├── Author.java       # Yazar entity'si
│   ├── Category.java     # Kategori entity'si
│   ├── Member.java       # Üye entity'si
│   ├── BorrowRecord.java # Ödünç alma kaydı
│   ├── Reservation.java  # Rezervasyon
│   └── enums/           # Enum sınıfları
├── repository/          # Data access layer
│   ├── GenericRepository.java # Generic repository interface
│   ├── BookRepository.java
│   ├── AuthorRepository.java
│   └── ...
├── service/            # Business logic layer
│   ├── GenericService.java # Generic service interface
│   ├── impl/          # Service implementasyonları
│   └── ...
└── LibraryManagementApplication.java # Ana uygulama sınıfı
```

## Özellikler

### 1. Kitap Yönetimi
- Kitap ekleme, düzenleme, silme
- Gelişmiş arama ve filtreleme
- Kategori ve yazar bazlı gruplama
- Stok takibi

### 2. Üye Yönetimi
- Üye kayıt ve profil yönetimi
- Ödünç alma geçmişi
- Aktif rezervasyonlar

### 3. Ödünç Alma Sistemi
- Kitap ödünç alma/iade
- Gecikme takibi
- Otomatik bildirimler

### 4. Rezervasyon Sistemi
- Kitap rezervasyonu
- Rezervasyon durumu takibi
- Otomatik rezervasyon iptali

### 5. İstatistikler ve Raporlama
- Popüler kitaplar
- Kategori bazlı istatistikler
- Üye aktivite raporları

### 6. Modern Web Arayüzü
- Responsive tasarım (Bootstrap)
- Modern UI/UX
- Font Awesome ikonları
- Kullanıcı dostu navigasyon

## Teknik Detaylar

### Backend
- **Java 8+**: Modern Java özellikleri
- **Spring Boot 3.x**: Ana framework
- **Spring Data JPA**: Veritabanı işlemleri
- **H2 Database**: In-memory veritabanı
- **Maven**: Build tool
- **Lombok**: Code generation

### Frontend
- **Thymeleaf**: Template engine
- **Bootstrap 5**: CSS framework
- **Font Awesome**: İkonlar
- **JavaScript**: Client-side interaktivite

### API Endpoints
- `GET /api/books` - Tüm kitapları listele
- `POST /api/books` - Yeni kitap ekle
- `GET /api/books/{id}` - Kitap detayı
- `PUT /api/books/{id}` - Kitap güncelle
- `DELETE /api/books/{id}` - Kitap sil
- `GET /api/books/search` - Kitap arama
- `GET /api/statistics` - İstatistikler

## Kurulum ve Çalıştırma

1. **Gereksinimler**:
   - Java 8 veya üzeri
   - Maven 3.6+

2. **Kurulum**:
   ```bash
   git clone [repository-url]
   cd library-management-system
   mvn clean install
   ```

3. **Çalıştırma**:
   ```bash
   mvn spring-boot:run
   ```

4. **Erişim**:
   - Web Arayüzü: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
   - API Docs: http://localhost:8080/api/books

## Deployment

### Local Development
- H2 in-memory database
- Port 8080
- Hot reload aktif

### Production Ready
- PostgreSQL database desteği
- Docker containerization
- Environment-based configuration
- Security enhancements

## Geliştirici Notları

### Kod Kalitesi
- Clean Code prensipleri
- SOLID principles
- Design patterns kullanımı
- Comprehensive error handling

### Performance
- Async operations
- Database optimization
- Caching strategies
- Connection pooling

### Security
- Input validation
- SQL injection prevention
- XSS protection
- CSRF protection

## Gelecek Geliştirmeler

1. **Authentication & Authorization**
2. **Email notifications**
3. **Advanced reporting**
4. **Mobile app**
5. **Integration with external APIs**
6. **Advanced search with Elasticsearch**

---

**Geliştirici**: [Öğrenci Adı]  
**Tarih**: Haziran 2024  
**Versiyon**: 1.0.0 