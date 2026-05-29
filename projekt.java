import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Wypozyczalny {
    boolean wypozycz(String uzytkownik);
    void zwroc();
    boolean czyDostepny();
}

abstract class Ksiazka implements Wypozyczalny {
    private String tytul;
    private String autor;
    private boolean dostepna;
    private String aktualnyUzytkownik;

    public Ksiazka(String tytul, String autor) {
        this.tytul = tytul;
        this.autor = autor;
        this.dostepna = true;
        this.aktualnyUzytkownik = "";
    }

    @Override
    public boolean wypozycz(String uzytkownik) {
        if (dostepna) {
            dostepna = false;
            this.aktualnyUzytkownik = uzytkownik;
            return true;
        }
        return false;
    }

    @Override
    public void zwroc() {
        dostepna = true;
        this.aktualnyUzytkownik = "";
    }

    @Override
    public boolean czyDostepny() {
        return dostepna;
    }

    public abstract String getTyp();

    // Gettery
    public String getTytul() { return tytul; }
    public String getAutor() { return autor; }
    public String getAktualnyUzytkownik() { return aktualnyUzytkownik; }
}

class LiteraturaPolska extends Ksiazka {
    private String epoka;

    public LiteraturaPolska(String tytul, String autor, String epoka) {
        super(tytul, autor);
        this.epoka = epoka;
    }
    @Override
    public String getTyp() { return "Literatura Polska (" + epoka + ")"; }
}

class KlasykaSwiatowa extends Ksiazka {
    private String kraj;

    public KlasykaSwiatowa(String tytul, String autor, String kraj) {
        super(tytul, autor);
        this.kraj = kraj;
    }
    @Override
    public String getTyp() { return "Klasyka Swiatowa (" + kraj + ")"; }
}

class FantastykaNaukowa extends Ksiazka {
    private String podgatunek;

    public FantastykaNaukowa(String tytul, String autor, String podgatunek) {
        super(tytul, autor);
        this.podgatunek = podgatunek;
    }
    @Override
    public String getTyp() { return "Sci-Fi (" + podgatunek + ")"; }
}


public class Main {
    private static List<Ksiazka> biblioteka = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Baza startowa
        biblioteka.add(new LiteraturaPolska("Pan Tadeusz", "Adam Mickiewicz", "Romantyzm"));
        biblioteka.add(new LiteraturaPolska("Kordian", "Juliusz Slowacki", "Romantyzm"));
        biblioteka.add(new LiteraturaPolska("Quo Vadis", "Henryk Sienkiewicz", "Pozytywizm"));
        biblioteka.add(new LiteraturaPolska("Cos ty Atenom zrobil, Sokratesie", "Cyprian Kamil Norwid", "Romantyzm"));
        biblioteka.add(new LiteraturaPolska("Ptaszki w klatce", "Ignacy Krasicki", "Oswiecenie"));
        
        biblioteka.add(new KlasykaSwiatowa("Zbrodnia i kara", "Fiodor Dostojewski", "Rosja"));
        biblioteka.add(new KlasykaSwiatowa("Bracia Karamazow", "Fiodor Dostojewski", "Rosja"));
        biblioteka.add(new KlasykaSwiatowa("Atomic Habits", "James Clear", "USA"));
        
        biblioteka.add(new FantastykaNaukowa("Rozaniec", "Rafal Kosik", "Cyberpunk"));
        biblioteka.add(new FantastykaNaukowa("Mars", "Rafal Kosik", "nieznane"));

        while (true) {
            System.out.println("\n========================================");
            System.out.println("          MENU BIBLIOTEKI               ");
            System.out.println("========================================");
            System.out.println("[1] Wyswietl liste i wypozycz ksiazke");
            System.out.println("[2] Zwroc ksiazke");
            System.out.println("[3] Dodaj nowa ksiazke");
            System.out.println("[4] Informacje o ksiazce");
            System.out.println("[5] Zamknij program");
            System.out.print("Wybierz opcje: ");

            String wybor = scanner.nextLine();

            switch (wybor) {
                case "1":
                    wypozycz();
                    break;
                case "2":
                    zwroc();
                    break;
                case "3":
                    dodajKsiazke();
                    break;
                case "4":
                    pokazInfoOKsiazce();
                    break;
                case "5":
                    System.out.println("Zamykanie systemu. Do zobaczenia!");
                    return;
                default:
                    System.out.println("Bledna opcja, sprobuj ponownie.");
            }
        }
    }

    // Opcja 1: Wypozyczanie ksiazki
    private static void wypozycz() {
        System.out.println("\n--- DOSTEPNE POZYCJE W SYSTEMIE ---");
        for (int i = 0; i < biblioteka.size(); i++) {
            Ksiazka k = biblioteka.get(i);
            String status = "";
            if (k.czyDostepny()) {
                status = "[WOLNA]";
            } else {
                status = "[ZAJETA przez: " + k.getAktualnyUzytkownik() + "]";
            }
            System.out.println("[" + i + "] \"" + k.getTytul() + "\" - " + k.getAutor() + " " + status);
        }

        System.out.print("\nWpisz numer indeksu ksiazki (lub wpisz 'wroc'): ");
        String wejscie = scanner.nextLine();

        if (wejscie.equalsIgnoreCase("wroc")) {
            System.out.println("Powrot do menu glownego.");
            return;
        }

        int indeks = Integer.parseInt(wejscie);

        if (indeks < 0 || indeks >= biblioteka.size()) {
            System.out.println("BLAD: Ksiazka o takim indeksie nie istnieje!");
            return;
        }

        Ksiazka wybrana = biblioteka.get(indeks);

        if (!wybrana.czyDostepny()) {
            System.out.println("BLAD: Ta ksiazka jest juz wypozyczona!");
            return;
        }

        System.out.print("Podaj swoje imie: ");
        String imie = scanner.nextLine();
        
        if (imie.trim().isEmpty()) {
            System.out.println("BLAD: Nie mozesz wypozyczyc ksiazki bez podania imienia!");
            return;
        }
        
        wybrana.wypozycz(imie);
        System.out.println("SUKCES: Pomyslnie wypozyczono ksiazke!");
    }

    // Opcja 2: Zwracanie ksiazki
    private static void zwroc() {
        System.out.println("\n--- KSIAZKI AKTUALNIE WYPOZYCZONE ---");
        boolean czyKtosWypozyczyl = false;

        for (int i = 0; i < biblioteka.size(); i++) {
            Ksiazka k = biblioteka.get(i);
            if (!k.czyDostepny()) {
                System.out.println("[" + i + "] \"" + k.getTytul() + "\" (Wypozyczyl: " + k.getAktualnyUzytkownik() + ")");
                czyKtosWypozyczyl = true;
            }
        }

        if (czyKtosWypozyczyl == false) {
            System.out.println("Wszystkie ksiazki sa w bibliotece.");
            return;
        }

        System.out.print("\nWpisz numer indeksu ksiazki do zwrotu (lub wpisz 'wroc'): ");
        String wejscie = scanner.nextLine();

        if (wejscie.equalsIgnoreCase("wroc")) {
            System.out.println("Powrot do menu glownego.");
            return;
        }

        int indeks = Integer.parseInt(wejscie);

        if (indeks < 0 || indeks >= biblioteka.size()) {
            System.out.println("BLAD: Nie ma takiego indeksu.");
            return;
        }

        Ksiazka wybrana = biblioteka.get(indeks);
        if (wybrana.czyDostepny()) {
            System.out.println("Ta ksiazka juz jest w bibliotece!");
        } else {
            wybrana.zwroc();
            System.out.println("SUKCES: Ksiazka zostala zwrocona.");
        }
    }

    // Opcja 3: Dodawanie nowej ksiazki
    private static void dodajKsiazke() {
        System.out.println("\n--- DODAWANIE NOWEJ KSIAZKI ---");
        System.out.println("Wybierz kategorie: 1-Literatura Polska, 2-Klasyka Swiatowa, 3-SciFi (lub wpisz 'wroc')");
        String kat = scanner.nextLine();

        if (kat.equalsIgnoreCase("wroc")) {
            System.out.println("Powrot do menu glownego.");
            return;
        }

        if (!kat.equals("1") && !kat.equals("2") && !kat.equals("3")) {
            System.out.println("BLAD: Niepoprawna kategoria!");
            return;
        }

        System.out.print("Tytul: ");
        String tytul = scanner.nextLine();
        if (tytul.trim().isEmpty()) {
            System.out.println("BLAD: Tytul nie moze byc pusty!");
            return;
        }

        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        if (autor.trim().isEmpty()) {
            System.out.println("BLAD: Autor nie moze byc pusty!");
            return;
        }

        if (kat.equals("1")) {
            System.out.print("Epoka: ");
            String epoka = scanner.nextLine();
            if (epoka.trim().isEmpty()) {
                System.out.println("BLAD: Epoka nie moze byc pusta!");
                return;
            }
            biblioteka.add(new LiteraturaPolska(tytul, autor, epoka));
            System.out.println("SUKCES: Ksiazka dodana!");
            
        } else if (kat.equals("2")) {
            System.out.print("Kraj pochodzenia: ");
            String kraj = scanner.nextLine();
            if (kraj.trim().isEmpty()) {
                System.out.println("BLAD: Kraj pochodzenia nie moze byc pusty!");
                return;
            }
            biblioteka.add(new KlasykaSwiatowa(tytul, autor, kraj));
            System.out.println("SUKCES: Ksiazka dodana!");
            
        } else if (kat.equals("3")) {
            System.out.print("Podgatunek: ");
            String podg = scanner.nextLine();
            if (podg.trim().isEmpty()) {
                System.out.println("BLAD: Podgatunek nie moze byc pusty!");
                return;
            }
            biblioteka.add(new FantastykaNaukowa(tytul, autor, podg));
            System.out.println("SUKCES: Ksiazka dodana!");
        }
    }

    // Opcja 4: Szczegolowe informacje o wybranej ksiazce
    private static void pokazInfoOKsiazce() {
        System.out.println("\n--- SPIS KSIAZEK W SYSTEMIE ---");
        for (int i = 0; i < biblioteka.size(); i++) {
            System.out.println("[" + i + "] " + biblioteka.get(i).getTytul());
        }

        System.out.print("\nWpisz numer indeksu ksiazki, aby zobaczyc info (lub wpisz 'wroc'): ");
        String wejscie = scanner.nextLine();

        if (wejscie.equalsIgnoreCase("wroc")) {
            System.out.println("Powrot do menu glownego.");
            return;
        }

        int indeks = Integer.parseInt(wejscie);

        if (indeks < 0 || indeks >= biblioteka.size()) {
            System.out.println("BLAD: Ksiazka o takim indeksie nie exists!");
            return;
        }

        Ksiazka wybrana = biblioteka.get(indeks);

        System.out.println("\n--- SZCZEGOLOWE INFORMACJE ---");
        System.out.println("Tytul: " + wybrana.getTytul());
        System.out.println("Autor: " + wybrana.getAutor());
        System.out.println("Kategoria/Typ: " + wybrana.getTyp());
        
        if (wybrana.czyDostepny()) {
            System.out.println("Status: Wolna (Można wypożyczyć)");
        } else {
            System.out.println("Status: Wypozyczona");
            System.out.println("Aktualny uzytkownik: " + wybrana.getAktualnyUzytkownik());
        }
        System.out.println("--------------------------------");
    }
}