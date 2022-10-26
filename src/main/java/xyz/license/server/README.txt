# Jak działa komunikacja szyfrowana?

1. Serwer przy każdym uruchomieniu generuje tymczasową 2048-bitową parę kluczy RSA używaną do szyfrowania komunikacji.
2. Klient łączy się
3. Klient -> Serwer: daj mi swój klucz
4. Klient <- Serwer: oto mój klucz publiczny
5. Klient -> Serwer: zaszyfrowane kluczem publicznym: losowy ciąg znaków a-zA-Z0-9{16} (klucz symetryczny)
6. Klient <-> Serwer: dalsza komunikacja zaszyfrowana kluczem symetrycznym (losowym ciągiem znaków a-zA-Z0-9{16})

# Jak działa proces weryfikacji?

Serwer generuje stałą 2048-bitową parę kluczy RSA przy pierwszym uruchomieniu.
Klienci powinni mieć zakodowany na sztywno klucz publiczny.

Poniższy proces weryfikacji zostaje przedstawiony jako plain-text, ale faktycznie jest szyfrowany kluczem symetrycznym opisanym wyżej

1. Klient wysyła login+hasło+timestamp zaszyfrowany za pomocą zakodowanego klucza publicznego
2. Serwer odbiera wiadomość i odszyfrowuje ją swoim kluczem prywatnym
3. Serwer sprawdza timestamp (różnica nie powinna wynosić więcej niż 10 sekund)
3. Serwer sprawdza bazę danych pod kątem danych uwierzytelniających (nieprawidłowe dane = komunikat o błędzie + rozłączenie)
4. Serwer sprawdza czy licencja nie wygasła (wygasła = komunikat o błędzie + rozłączenie)
5. Serwer zwraca timestamp+status zaszyfrowane swoim kluczem prywatnym (status = "VALID|INVALID")
6. Klient odbiera wiadomość, odszyfrowuje ją i sprawdza status i timestamp
7. Klient decyduje, czy odpowiedź serwera mu pasuje, czy nie

W ten sposób skutecznie uniemożliwiamy ominięcie weryfikacji licencji za pomocą modyfikacji hostów i postawienia własnego serwera licencji,
ponieważ odpowiedź jest zaszyfrowana kluczem prywatnym RSA serwera. Nasłuchanie odpowiedzi też nic tutaj nie da, ponieważ
klient sprawdza timestamp odpowiedzi serwera, więc pojedyncza odpowiedź serwera będzie zdatna do użytku maksymalnie 10 sekund.