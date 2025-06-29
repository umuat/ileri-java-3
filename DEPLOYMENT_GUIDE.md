# 🚀 Deployment Rehberi

Bu rehber, Kütüphane Yönetim Sistemi projesini farklı ortamlarda nasıl deploy edeceğinizi açıklar.

## 📋 Ön Gereksinimler

- Java 8 veya üzeri
- Maven 3.6+
- Git
- Docker (opsiyonel)
- PostgreSQL (production için)

## 🏠 Local Development

### 1. H2 Database ile (Varsayılan)
```bash
# Projeyi klonlayın
git clone https://github.com/[kullanici-adi]/library-management-system.git
cd library-management-system

# Bağımlılıkları yükleyin
mvn clean install

# Uygulamayı çalıştırın
mvn spring-boot:run
```

**Erişim:**
- Web Arayüzü: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- API: http://localhost:8080/api/books

### 2. PostgreSQL ile Local
```bash
# PostgreSQL'i başlatın (Docker ile)
docker run --name postgres-library -e POSTGRES_DB=librarydb -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres:13-alpine

# Uygulamayı PostgreSQL profili ile çalıştırın
mvn spring-boot:run -Dspring.profiles.active=docker
```

## 🐳 Docker ile Deployment

### 1. Docker Compose (Önerilen)
```bash
# Tüm servisleri başlatın
docker-compose up -d

# Logları kontrol edin
docker-compose logs -f

# Servisleri durdurun
docker-compose down
```

### 2. Manuel Docker Build
```bash
# Docker image oluşturun
docker build -t library-management-system .

# Container'ı çalıştırın
docker run -p 8080:8080 library-management-system
```

## ☁️ Cloud Deployment

### 1. Heroku
```bash
# Heroku CLI kurulumu
# https://devcenter.heroku.com/articles/heroku-cli

# Heroku'da uygulama oluşturun
heroku create library-management-system

# PostgreSQL addon ekleyin
heroku addons:create heroku-postgresql:hobby-dev

# Deploy edin
git push heroku main

# Uygulamayı açın
heroku open
```

### 2. Railway
```bash
# Railway CLI kurulumu
npm install -g @railway/cli

# Railway'e giriş yapın
railway login

# Projeyi başlatın
railway init

# Deploy edin
railway up
```

### 3. Render
```bash
# Render.com'da yeni Web Service oluşturun
# Build Command: mvn clean package
# Start Command: java -jar target/library-management-system-1.0.0.jar
# Environment Variables:
#   SPRING_PROFILES_ACTIVE=docker
#   SPRING_DATASOURCE_URL=jdbc:postgresql://...
```

### 4. DigitalOcean App Platform
```bash
# DigitalOcean CLI kurulumu
# https://docs.digitalocean.com/reference/doctl/how-to/install/

# App oluşturun
doctl apps create --spec app.yaml
```

## 🗄️ Veritabanı Konfigürasyonu

### H2 Database (Development)
```properties
spring.datasource.url=jdbc:h2:mem:librarydb
spring.datasource.driverClassName=org.h2.Driver
spring.h2.console.enabled=true
```

### PostgreSQL (Production)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/librarydb
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
```

## 🔧 Environment Variables

### Gerekli Environment Variables
```bash
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/librarydb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=password

# Application
SPRING_PROFILES_ACTIVE=docker
SERVER_PORT=8080

# Security (opsiyonel)
SPRING_SECURITY_USER_NAME=admin
SPRING_SECURITY_USER_PASSWORD=admin123
```

## 📊 Monitoring ve Logging

### Application Metrics
```bash
# Health check
curl http://localhost:8080/actuator/health

# Application info
curl http://localhost:8080/actuator/info
```

### Log Monitoring
```bash
# Docker logs
docker-compose logs -f app

# Application logs
tail -f logs/application.log
```

## 🔒 Security Considerations

### Production Security Checklist
- [ ] HTTPS enabled
- [ ] Strong passwords
- [ ] Database connection encryption
- [ ] Input validation
- [ ] SQL injection prevention
- [ ] XSS protection
- [ ] CSRF protection
- [ ] Rate limiting

### Basic Security Configuration
```properties
# HTTPS
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=password

# Security headers
server.servlet.session.cookie.secure=true
server.servlet.session.cookie.http-only=true
```

## 🚀 Performance Optimization

### JVM Tuning
```bash
# Memory optimization
java -Xmx1g -Xms512m -jar target/library-management-system-1.0.0.jar

# Garbage collection
java -XX:+UseG1GC -jar target/library-management-system-1.0.0.jar
```

### Database Optimization
```sql
-- Indexes for better performance
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_author_id ON books(author_id);
CREATE INDEX idx_borrow_records_member_id ON borrow_records(member_id);
```

## 📝 Deployment Checklist

### Pre-Deployment
- [ ] All tests passing
- [ ] Code review completed
- [ ] Environment variables configured
- [ ] Database schema updated
- [ ] Security review completed

### Deployment
- [ ] Backup existing data
- [ ] Deploy new version
- [ ] Run health checks
- [ ] Verify functionality
- [ ] Monitor performance

### Post-Deployment
- [ ] Update documentation
- [ ] Notify stakeholders
- [ ] Monitor error logs
- [ ] Performance monitoring

## 🆘 Troubleshooting

### Common Issues

#### 1. Port Already in Use
```bash
# Check what's using port 8080
netstat -ano | findstr :8080

# Kill the process
taskkill /PID <process_id> /F
```

#### 2. Database Connection Issues
```bash
# Test database connection
psql -h localhost -U postgres -d librarydb

# Check application logs
tail -f logs/application.log
```

#### 3. Memory Issues
```bash
# Increase heap size
export JAVA_OPTS="-Xmx2g -Xms1g"
mvn spring-boot:run
```

#### 4. Docker Issues
```bash
# Clean up Docker
docker system prune -a

# Rebuild image
docker-compose build --no-cache
```

## 📞 Support

Deployment ile ilgili sorunlar için:

1. **Logları kontrol edin**: `docker-compose logs -f`
2. **Health check yapın**: `curl http://localhost:8080/actuator/health`
3. **Database bağlantısını test edin**
4. **Environment variables'ları kontrol edin**

---

**Son Güncelleme**: Haziran 2024  
**Versiyon**: 1.0.0 