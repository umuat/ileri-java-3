package com.library.config;

import com.library.entity.*;
import com.library.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Veri Yükleyici
 * 
 * Bu sınıf uygulama başlangıcında örnek verileri yükler.
 * CommandLineRunner interface'ini implement eder.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public DataLoader(AuthorRepository authorRepository,
                     BookRepository bookRepository,
                     CategoryRepository categoryRepository,
                     MemberRepository memberRepository,
                     BorrowRecordRepository borrowRecordRepository,
                     ReservationRepository reservationRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.memberRepository = memberRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Örnek veriler yükleniyor...");

        // Verileri sırayla oluştur
        createCategories();
        createAuthors();
        createBooks();
        createMembers();
        createBorrowRecords();
        createReservations();

        log.info("Örnek veriler başarıyla yüklendi!");
    }

    private void createCategories() {
        log.info("Kategoriler oluşturuluyor...");

        // Kategorileri oluştur
        Category roman = new Category();
        roman.setName("Roman");
        roman.setDescription("Roman türündeki kitaplar");
        roman.setColorCode("#FF6B6B");
        categoryRepository.save(roman);

        Category bilimKurgu = new Category();
        bilimKurgu.setName("Bilim Kurgu");
        bilimKurgu.setDescription("Bilim kurgu türündeki kitaplar");
        bilimKurgu.setColorCode("#4ECDC4");
        categoryRepository.save(bilimKurgu);

        Category felsefe = new Category();
        felsefe.setName("Felsefe");
        felsefe.setDescription("Felsefe türündeki kitaplar");
        felsefe.setColorCode("#45B7D1");
        categoryRepository.save(felsefe);

        Category psikoloji = new Category();
        psikoloji.setName("Psikoloji");
        psikoloji.setDescription("Psikoloji türündeki kitaplar");
        psikoloji.setColorCode("#96CEB4");
        categoryRepository.save(psikoloji);

        Category tarih = new Category();
        tarih.setName("Tarih");
        tarih.setDescription("Tarih türündeki kitaplar");
        tarih.setColorCode("#FFEAA7");
        categoryRepository.save(tarih);

        Category teknoloji = new Category();
        teknoloji.setName("Teknoloji");
        teknoloji.setDescription("Teknoloji türündeki kitaplar");
        teknoloji.setColorCode("#DDA0DD");
        categoryRepository.save(teknoloji);
    }

    private void createAuthors() {
        log.info("Yazarlar oluşturuluyor...");

        // Yazarları oluştur
        Author orhanPamuk = new Author();
        orhanPamuk.setName("Orhan Pamuk");
        orhanPamuk.setBiography("Nobel Edebiyat Ödülü sahibi Türk yazar");
        orhanPamuk.setEmail("orhan.pamuk@example.com");
        orhanPamuk.setBirthYear(1952);
        orhanPamuk.setNationality("Türk");
        authorRepository.save(orhanPamuk);

        Author georgeOrwell = new Author();
        georgeOrwell.setName("George Orwell");
        georgeOrwell.setBiography("İngiliz yazar ve gazeteci");
        georgeOrwell.setEmail("george.orwell@example.com");
        georgeOrwell.setBirthYear(1903);
        georgeOrwell.setNationality("İngiliz");
        authorRepository.save(georgeOrwell);

        Author albertCamus = new Author();
        albertCamus.setName("Albert Camus");
        albertCamus.setBiography("Fransız yazar ve filozof");
        albertCamus.setEmail("albert.camus@example.com");
        albertCamus.setBirthYear(1913);
        albertCamus.setNationality("Fransız");
        authorRepository.save(albertCamus);

        Author stephenKing = new Author();
        stephenKing.setName("Stephen King");
        stephenKing.setBiography("Amerikalı korku ve gerilim yazarı");
        stephenKing.setEmail("stephen.king@example.com");
        stephenKing.setBirthYear(1947);
        stephenKing.setNationality("Amerikalı");
        authorRepository.save(stephenKing);
    }

    private void createBooks() {
        log.info("Kitaplar oluşturuluyor...");

        // Kategorileri ve yazarları al
        Category roman = categoryRepository.findByName("Roman").orElse(null);
        Category bilimKurgu = categoryRepository.findByName("Bilim Kurgu").orElse(null);
        Category felsefe = categoryRepository.findByName("Felsefe").orElse(null);
        Category psikoloji = categoryRepository.findByName("Psikoloji").orElse(null);
        Category tarih = categoryRepository.findByName("Tarih").orElse(null);
        Category teknoloji = categoryRepository.findByName("Teknoloji").orElse(null);

        Author orhanPamuk = authorRepository.findByName("Orhan Pamuk").orElse(null);
        Author georgeOrwell = authorRepository.findByName("George Orwell").orElse(null);
        Author albertCamus = authorRepository.findByName("Albert Camus").orElse(null);
        Author stephenKing = authorRepository.findByName("Stephen King").orElse(null);

        // Kitap 1: Kar
        Book kar = new Book();
        kar.setTitle("Kar");
        kar.setIsbn("9789750719386");
        kar.setDescription("Kars'ta geçen politik roman");
        kar.setPageCount(440);
        kar.setPublicationYear(2002);
        kar.setPublisher("İletişim Yayınları");
        kar.setLanguage("Türkçe");
        kar.setPrice(new BigDecimal("45.00"));
        kar.setStatus(BookStatus.AVAILABLE);
        kar.setLocation("A Blok - Raf 1");
        kar.setAuthor(orhanPamuk);
        
        // Kategorileri manuel olarak ekle
        if (roman != null) {
            kar.getCategories().add(roman);
        }
        bookRepository.save(kar);

        // Kitap 2: 1984
        Book nineteenEightyFour = new Book();
        nineteenEightyFour.setTitle("1984");
        nineteenEightyFour.setIsbn("9789750719393");
        nineteenEightyFour.setDescription("Distopik bir gelecek tasarımı");
        nineteenEightyFour.setPageCount(328);
        nineteenEightyFour.setPublicationYear(1949);
        nineteenEightyFour.setPublisher("Can Yayınları");
        nineteenEightyFour.setLanguage("Türkçe");
        nineteenEightyFour.setPrice(new BigDecimal("35.00"));
        nineteenEightyFour.setStatus(BookStatus.BORROWED);
        nineteenEightyFour.setLocation("B Blok - Raf 2");
        nineteenEightyFour.setAuthor(georgeOrwell);
        
        if (bilimKurgu != null) nineteenEightyFour.getCategories().add(bilimKurgu);
        if (roman != null) nineteenEightyFour.getCategories().add(roman);
        bookRepository.save(nineteenEightyFour);

        // Kitap 3: Yabancı
        Book yabanci = new Book();
        yabanci.setTitle("Yabancı");
        yabanci.setIsbn("9789750719400");
        yabanci.setDescription("Varoluşçu felsefenin edebi temsili");
        yabanci.setPageCount(184);
        yabanci.setPublicationYear(1942);
        yabanci.setPublisher("Can Yayınları");
        yabanci.setLanguage("Türkçe");
        yabanci.setPrice(new BigDecimal("25.00"));
        yabanci.setStatus(BookStatus.AVAILABLE);
        yabanci.setLocation("C Blok - Raf 3");
        yabanci.setAuthor(albertCamus);
        
        if (roman != null) yabanci.getCategories().add(roman);
        if (felsefe != null) yabanci.getCategories().add(felsefe);
        bookRepository.save(yabanci);

        // Kitap 4: Şeker Portakalı
        Book sekerPortakali = new Book();
        sekerPortakali.setTitle("Şeker Portakalı");
        sekerPortakali.setIsbn("9789750719417");
        sekerPortakali.setDescription("Çocuk gözüyle dünyayı anlatan roman");
        sekerPortakali.setPageCount(272);
        sekerPortakali.setPublicationYear(1968);
        sekerPortakali.setPublisher("Can Yayınları");
        sekerPortakali.setLanguage("Türkçe");
        sekerPortakali.setPrice(new BigDecimal("30.00"));
        sekerPortakali.setStatus(BookStatus.AVAILABLE);
        sekerPortakali.setLocation("A Blok - Raf 4");
        sekerPortakali.setAuthor(null); // Anonim
        
        if (roman != null) sekerPortakali.getCategories().add(roman);
        bookRepository.save(sekerPortakali);

        // Kitap 5: The Shining
        Book shining = new Book();
        shining.setTitle("The Shining");
        shining.setIsbn("9789750719424");
        shining.setDescription("Korku türünün başyapıtlarından");
        shining.setPageCount(447);
        shining.setPublicationYear(1977);
        shining.setPublisher("Altın Kitaplar");
        shining.setLanguage("İngilizce");
        shining.setPrice(new BigDecimal("40.00"));
        shining.setStatus(BookStatus.RESERVED);
        shining.setLocation("D Blok - Raf 5");
        shining.setAuthor(stephenKing);
        
        if (roman != null) shining.getCategories().add(roman);
        if (psikoloji != null) shining.getCategories().add(psikoloji);
        bookRepository.save(shining);

        // Kitap 6: Uçurtma Avcısı
        Book ucurtmaAvcisi = new Book();
        ucurtmaAvcisi.setTitle("Uçurtma Avcısı");
        ucurtmaAvcisi.setIsbn("9789750719431");
        ucurtmaAvcisi.setDescription("Afganistan'da geçen dostluk hikayesi");
        ucurtmaAvcisi.setPageCount(368);
        ucurtmaAvcisi.setPublicationYear(2003);
        ucurtmaAvcisi.setPublisher("Everest Yayınları");
        ucurtmaAvcisi.setLanguage("Türkçe");
        ucurtmaAvcisi.setPrice(new BigDecimal("35.00"));
        ucurtmaAvcisi.setStatus(BookStatus.AVAILABLE);
        ucurtmaAvcisi.setLocation("B Blok - Raf 6");
        ucurtmaAvcisi.setAuthor(null); // Khaled Hosseini
        
        if (roman != null) ucurtmaAvcisi.getCategories().add(roman);
        if (tarih != null) ucurtmaAvcisi.getCategories().add(tarih);
        bookRepository.save(ucurtmaAvcisi);

        // Kitap 7: Clean Code
        Book cleanCode = new Book();
        cleanCode.setTitle("Clean Code");
        cleanCode.setIsbn("9789750719448");
        cleanCode.setDescription("Yazılım geliştirme için temiz kod prensipleri");
        cleanCode.setPageCount(464);
        cleanCode.setPublicationYear(2008);
        cleanCode.setPublisher("Prentice Hall");
        cleanCode.setLanguage("İngilizce");
        cleanCode.setPrice(new BigDecimal("60.00"));
        cleanCode.setStatus(BookStatus.AVAILABLE);
        cleanCode.setLocation("E Blok - Raf 7");
        cleanCode.setAuthor(null); // Robert C. Martin
        
        if (teknoloji != null) cleanCode.getCategories().add(teknoloji);
        bookRepository.save(cleanCode);

        // Kitap 8: Sefiller
        Book sefiller = new Book();
        sefiller.setTitle("Sefiller");
        sefiller.setIsbn("9789750719455");
        sefiller.setDescription("Victor Hugo'nun başyapıtı");
        sefiller.setPageCount(1488);
        sefiller.setPublicationYear(1862);
        sefiller.setPublisher("İş Bankası Kültür Yayınları");
        sefiller.setLanguage("Türkçe");
        sefiller.setPrice(new BigDecimal("80.00"));
        sefiller.setStatus(BookStatus.AVAILABLE);
        sefiller.setLocation("F Blok - Raf 8");
        sefiller.setAuthor(null); // Victor Hugo
        
        if (roman != null) sefiller.getCategories().add(roman);
        if (tarih != null) sefiller.getCategories().add(tarih);
        bookRepository.save(sefiller);
    }

    private void createMembers() {
        log.info("Üyeler oluşturuluyor...");

        Member member1 = new Member();
        member1.setFirstName("Ahmet");
        member1.setLastName("Yılmaz");
        member1.setEmail("ahmet.yilmaz@example.com");
        member1.setPhone("0532 123 45 67");
        member1.setBirthDate(LocalDate.of(1985, 5, 15));
        member1.setAddress("Kadıköy, İstanbul");
        member1.setMembershipNumber("MEM001");
        member1.setActive(true);
        member1.setMembershipStartDate(LocalDate.of(2023, 1, 1));
        member1.setMembershipEndDate(LocalDate.of(2024, 12, 31));
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setFirstName("Ayşe");
        member2.setLastName("Demir");
        member2.setEmail("ayse.demir@example.com");
        member2.setPhone("0533 234 56 78");
        member2.setBirthDate(LocalDate.of(1990, 8, 22));
        member2.setAddress("Beşiktaş, İstanbul");
        member2.setMembershipNumber("MEM002");
        member2.setActive(true);
        member2.setMembershipStartDate(LocalDate.of(2023, 3, 15));
        member2.setMembershipEndDate(LocalDate.of(2024, 12, 31));
        memberRepository.save(member2);

        Member member3 = new Member();
        member3.setFirstName("Mehmet");
        member3.setLastName("Kaya");
        member3.setEmail("mehmet.kaya@example.com");
        member3.setPhone("0534 345 67 89");
        member3.setBirthDate(LocalDate.of(1978, 12, 10));
        member3.setAddress("Ankara, Çankaya");
        member3.setMembershipNumber("MEM003");
        member3.setActive(false);
        member3.setMembershipStartDate(LocalDate.of(2022, 6, 1));
        member3.setMembershipEndDate(LocalDate.of(2023, 5, 31));
        memberRepository.save(member3);
    }

    private void createBorrowRecords() {
        log.info("Ödünç alma kayıtları oluşturuluyor...");

        // Üyeleri al
        Member member1 = memberRepository.findByEmail("ahmet.yilmaz@example.com").orElse(null);
        Member member2 = memberRepository.findByEmail("ayse.demir@example.com").orElse(null);

        // Kitapları al
        Book book1 = bookRepository.findByIsbn("9789750719393").orElse(null); // 1984
        Book book2 = bookRepository.findByIsbn("9789750719424").orElse(null); // The Shining

        if (member1 != null && book1 != null) {
            BorrowRecord record1 = new BorrowRecord();
            record1.setBook(book1);
            record1.setMember(member1);
            record1.setBorrowDate(LocalDate.now().minusDays(5));
            record1.setDueDate(LocalDate.now().plusDays(9));
            record1.calculateDefaultDueDate();
            borrowRecordRepository.save(record1);
        }

        if (member2 != null && book2 != null) {
            BorrowRecord record2 = new BorrowRecord();
            record2.setBook(book2);
            record2.setMember(member2);
            record2.setBorrowDate(LocalDate.now().minusDays(10));
            record2.setDueDate(LocalDate.now().minusDays(2));
            record2.calculateDefaultDueDate();
            borrowRecordRepository.save(record2);
        }
    }

    private void createReservations() {
        log.info("Rezervasyonlar oluşturuluyor...");

        // Üyeleri al
        Member member1 = memberRepository.findByEmail("ahmet.yilmaz@example.com").orElse(null);
        Member member2 = memberRepository.findByEmail("ayse.demir@example.com").orElse(null);

        // Kitapları al
        Book book1 = bookRepository.findByIsbn("9789750719386").orElse(null); // Kar
        Book book2 = bookRepository.findByIsbn("9789750719400").orElse(null); // Yabancı

        if (member1 != null && book1 != null) {
            Reservation reservation1 = new Reservation();
            reservation1.setBook(book1);
            reservation1.setMember(member1);
            reservation1.setReservationDate(LocalDate.now().minusDays(2));
            reservation1.setStatus(ReservationStatus.PENDING);
            reservation1.calculateDefaultExpiryDate();
            reservationRepository.save(reservation1);
        }

        if (member2 != null && book2 != null) {
            Reservation reservation2 = new Reservation();
            reservation2.setBook(book2);
            reservation2.setMember(member2);
            reservation2.setReservationDate(LocalDate.now().minusDays(1));
            reservation2.setStatus(ReservationStatus.PENDING);
            reservation2.calculateDefaultExpiryDate();
            reservationRepository.save(reservation2);
        }
    }
} 