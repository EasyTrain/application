-- Postman test GET request
-- -- -- http://localhost:8081/easytrain/api/pay?encryptedData=urC1zGwJg7FkeBgjhwIx2IyWhMhtCAB/4ktUu49UiYI=&billValue=200

INSERT INTO public.payments(address, balance, card_holder, card_number, cvc, email, encrypted_data, expiry_date, full_name, phone_number)
VALUES ( 'Muster strasse 1000, 99999 Muster', 100000, 'John Doe', '1234 1234 1234 2235', '321', 'Johndoe@client.com', 'urC1zGwJg7FkeBgjhwIx2IyWhMhtCAB/4ktUu49UiYI=',
         '9912', 'John Doe', '+491234123455');