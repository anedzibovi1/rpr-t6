package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class Controller {
    public TextField prezime;
    public TextField ime;
    public TextField indeks;
    public TextField jmbg;
    public DatePicker datum;
    public ComboBox<String> mjesto;
    public ChoiceBox<String> odsjek;
    public ChoiceBox<String> godina;
    public ChoiceBox<String> ciklus;
    public TextField adresa;
    public TextField telefon;
    public TextField email;
    public Button prijava;
    public RadioButton redovan;
    public RadioButton redovansam;
    public CheckBox borac;
    public boolean IspravnoIme;
    public boolean IspravnoPrezime;
    public boolean IspravanIndeks;
    public boolean IspravanJMBG;
    public boolean IspravanDatum;
    public boolean IspravanEmail;
    public boolean IspravanTelefon;
    public boolean IspravnoMjesto;

    public boolean testIme(String s) {
        if(s.trim().isEmpty() || s.length()>20) return false;
        for (char c: s.toCharArray())
            if(!Character.isAlphabetic(c)) return false;
        return true;
    }

    public boolean testPrezime(String s) {
        if(s.trim().isEmpty() || s.length()>20) return false;
        for (char c: s.toCharArray())
            if(!Character.isAlphabetic(c)) return false;
        return true;
    }

    public boolean testIndeksa(String s) {
        if(s.length()>5) return false;
        for (char c: s.toCharArray())
            if(Character.isAlphabetic(c)) return false;
        return true;
    }

    private String getJMBG() {
        return this.jmbg.getText();
    }
    private int getJmbgDan(String s) {
        return Integer.parseInt(s.substring(0, 2));
    }

    private int getJmbgMjesec(String s) {
        return Integer.parseInt(s.substring(2, 4));
    }

    private int getJmbgGodina(String s) {
        int godina = Integer.parseInt(s.substring(4, 7));
        if (godina >= 900)
            godina += 1000;
        else
            godina += 2000;
        return godina;
    }
    public boolean testJMBG(String s) {
        int[] mjeseci={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if(s.length()!=13) return false;
        for (char c: s.toCharArray())
            if(Character.isAlphabetic(c)) return false;
        int dan= getJmbgDan(s);
        int mjesec= getJmbgMjesec(s);
        int godina= getJmbgGodina(s);
        if(mjesec<=0 || mjesec>=12) return false;
        if (godina % 400 == 0 || (godina % 100 != 0 && godina % 4 == 0))
            mjeseci[1] = 29;
        if(dan<=0 || dan>mjeseci[mjesec-1]) return false;
        return true;
    }

    public boolean testDatuma() {
        if(datum.getValue()==null) return false;
        if(datum.getValue().getDayOfMonth() != getJmbgDan(getJMBG()) || datum.getValue().getMonthValue() != getJmbgMjesec(getJMBG()) || datum.getValue().getYear() != getJmbgGodina(getJMBG()))
            return false;
        return !datum.getValue().isAfter(LocalDate.now());
    }

    private boolean testTelefon(String s) {
        if (s.equals(""))
            return true;
        if ((s.length() != 9 && s.length() != 10))
            return false;
        for (char c: s.toCharArray())
            if(Character.isAlphabetic(c)) return false;
        int pozivniBroj = Integer.parseInt(s.substring(1, 3));
        if (!s.substring(0, 1).equals("0") || pozivniBroj < 30 || (pozivniBroj > 39 && pozivniBroj < 49) || (pozivniBroj > 67 && pozivniBroj < 70) || pozivniBroj > 70)
            return false;
        return true;
    }

    private boolean testEmail(String s) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(s);
        return m.matches();
    }

    private boolean testMjesta(String unos) {
        return (!mjesto.getEditor().getText().equals("") && unos.length() >= 1 && unos.length() <= 40 && Character.isUpperCase(unos.charAt(0)));
    }

    private void popuniOdsjek() {
        odsjek.getItems().addAll(
                "Automatika i elektronika",
                "Elektroenergetika",
                "Računarstvo",
                "Telekomunikacije"
        );
    }

    private void popuniGodinu() {
        ObservableList<String> god = FXCollections.observableArrayList(
                new String("Element 1"),
                new String("Element 2")
        );
        godina.setItems(god);
        godina.getSelectionModel().selectFirst();
    }

    private void popuniCiklus() {
        ciklus.getItems().add("Bachelor");
        ciklus.getItems().add("Master");
        ciklus.getItems().add("Doktorski studij");
        ciklus.getItems().add("Stručni studij");
    }

    private void popuniMjesto() {
        mjesto.getItems().add("Sarajevo");
        mjesto.getItems().add("Banja Luka");
        mjesto.getItems().add("Tuzla");
        mjesto.getItems().add("Zenica");
        mjesto.getItems().add("Bijeljina");
        mjesto.getItems().add("Mostar");
    }

    @FXML
    public void Initialize() {
        popuniCiklus();
        popuniGodinu();
        popuniMjesto();
        popuniOdsjek();

        IspravanJMBG=false;
        IspravanIndeks=false;
        IspravnoIme=false;
        IspravnoPrezime=false;
        IspravanDatum=false;
        IspravanEmail=false;
        IspravanTelefon=false;
        IspravnoMjesto=false;

        ime.getStyleClass().add("neispravno");
        prezime.getStyleClass().add("neispravno");
        jmbg.getStyleClass().add("neispravno");
        indeks.getStyleClass().add("neispravno");
        email.getStyleClass().add("neispravno");

        ToggleGroup radioButtonGrupa = new ToggleGroup();
        redovan.setToggleGroup(radioButtonGrupa);
        redovan.setSelected(true);
        redovansam.setToggleGroup(radioButtonGrupa);

        ime.textProperty().addListener((observable, oldValue, newValue) -> {
            if (testIme(newValue)) {
                ime.getStyleClass().removeAll("neispravno");
                ime.getStyleClass().add("ispravno");
                IspravnoIme = true;
            } else {
                ime.getStyleClass().removeAll("ispravno");
                ime.getStyleClass().add("neispravno");
                IspravnoIme = false;
            }
        });

        prezime.textProperty().addListener((observable, oldValue, newValue) -> {
            if (testPrezime(newValue)) {
                prezime.getStyleClass().removeAll("neispravno");
                prezime.getStyleClass().add("ispravno");
                IspravnoPrezime = true;
            } else {
                prezime.getStyleClass().removeAll("ispravno");
                prezime.getStyleClass().add("neispravno");
                IspravnoPrezime = false;
            }
        });

        indeks.textProperty().addListener((observable, oldValue, newValue) -> {
            if (testIndeksa(newValue)) {
                indeks.getStyleClass().removeAll("neispravno");
                indeks.getStyleClass().add("ispravno");
                IspravanIndeks = true;
            } else {
                indeks.getStyleClass().removeAll("ispravno");
                indeks.getStyleClass().add("neispravno");
                IspravanIndeks = false;
            }
        });

        jmbg.textProperty().addListener((observable, oldValue, newValue) -> {
            if (testJMBG(newValue)) {
                jmbg.getStyleClass().removeAll("neispravno");
                jmbg.getStyleClass().add("ispravno");
                IspravanJMBG = true;
            } else {
                jmbg.getStyleClass().removeAll("ispravno");
                jmbg.getStyleClass().add("neispravno");
                IspravanJMBG = false;
            }
        });

        telefon.textProperty().addListener((observable, oldValue, newValue) -> {
            if (testTelefon(newValue)) {
                telefon.getStyleClass().removeAll("neispravno");
                telefon.getStyleClass().add("ispravno");
                IspravanTelefon = true;
            } else {
                telefon.getStyleClass().removeAll("ispravno");
                telefon.getStyleClass().add("neispravno");
                IspravanTelefon = false;
            }
        });

        email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (testEmail(newValue)) {
                email.getStyleClass().removeAll("neispravno");
                email.getStyleClass().add("ispravno");
                IspravanEmail = true;
            } else {
                email.getStyleClass().removeAll("ispravno");
                email.getStyleClass().add("neispravno");
                IspravanEmail = false;
            }
        });

        mjesto.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (testMjesta((String) newValue)) {
                mjesto.getStyleClass().removeAll("neispravno");
                mjesto.getStyleClass().add("ispravno");
                IspravnoMjesto = true;
            } else {
                mjesto.getStyleClass().removeAll("ispravno");
                mjesto.getStyleClass().add("neispravno");
                IspravnoMjesto = false;
            }
        });

        datum.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (testDatuma()) {
                datum.getStyleClass().removeAll("neispravno");
                datum.getStyleClass().add("ispravno");
                IspravanDatum = true;
            } else {
                datum.getStyleClass().removeAll("ispravno");
                datum.getStyleClass().add("neispravno");
                IspravanDatum = false;
            }
        });
    }

    public boolean testFormulara() {
        return IspravnoIme && IspravnoPrezime && IspravanIndeks && IspravanJMBG && IspravanDatum && IspravanEmail && IspravnoMjesto && IspravanTelefon;
    }

    public void kliknutaPrijava(javafx.event.ActionEvent actionEvent) throws Exception {
        if(testFormulara()) {
            String ispis = "";
            ispis = "Ime: " + ime.getText();
            ispis += "\nPrezime: " + prezime.getText();
            ispis += "\nBroj indeksa: " + indeks.getText();
            ispis += "\nJMBG: " + jmbg.getText();
            ispis += "\nDatum rođenja: " + datum.getValue();
            ispis += "\nMjesto rođenja: " + mjesto.getValue();
            if (adresa.getText().equals(""))
                ispis += "\nKontakt adresa: /";
            else
                ispis += "\nKontakt adresa: " + adresa.getText();
            if (telefon.getText().equals(""))
                ispis += "\nKontakt telefon: /";
            else
                ispis += "\nKontakt telefon: " + telefon.getText();
            ispis += "\nEmail adresa: " + email.getText();
            ispis += "\nOdsjek: " + odsjek.getValue();
            ispis += "\nGodina studija: " + godina.getValue();
            ispis += "\nCiklus studija: " + ciklus.getValue();
            ispis += "\nStatus: ";
            if (redovan.isSelected())
                ispis += redovan.getText();
            else
                ispis += redovansam.getText();
            ispis += "\n" + borac.getText() + ": ";
            if (borac.isSelected())
                ispis += "✓";
            else
                ispis += "/";
            System.out.println(ispis);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nije validno");
            alert.setHeaderText("Unos podataka o studentu nije validan!");
            alert.setContentText("Crvenom bojom označeni su podaci koji su pogrešni ili nedostaju.");
            alert.show();
        }
    }

}

