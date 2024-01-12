INSERT INTO public.supermarkets (id, label) VALUES
('1', 'Supermarché du Centre'),
('2', 'Supermarché de Quartier');

INSERT INTO users (id, email, firstname, lastname, password, phonenumber, profilepictureurl, role) VALUES
(1, 'john.doe@example.com', 'John', 'Doe', 'motdepasse123', '123-456-7890', 'https://upload.wikimedia.org/wikipedia/commons/thumb/5//John_and_Jane_Doe_Headstones.jpg/220px-John_and_Jane_Doe_Headstones.jpg', 0),
(2, 'jane.smith@example.com', 'Jane', 'Smith', 'motdepasse456', '987-654-3210', 'jane_profile.jpg', 1),
('3', 'alexandre.morin94700@gmail.com', 'Alexandre', 'Morin', 'motdepasse789', '0681306593', 'alexandre_profile.jpg', 0);


INSERT INTO caddies (id, label, state, supermarketId) VALUES
('1', 'Caddie 1', '0', '1'),
('2', 'Caddie 2', '0', '2'),
('10', 'Caddie 10', '0', '1'),
('11', 'Caddie 11', '0', '2'),
('3', 'Caddie 3', '0', '1'),
('4', 'Caddie 4', '0', '1'),
('5', 'Caddie 5', '0', '1'),
('6', 'Caddie 6', '0', '2'),
('7', 'Caddie 7', '0', '2'),
('8', 'Caddie 8', '0', '1'),
('9', 'Caddie 9', '0', '1');


-- Ajout de catégories
INSERT INTO categories (id, imageurl , "label") VALUES
('1', 'category1.jpg', 'Épicerie'),
('2', 'category2.jpg', 'Produits Frais'),
('3', 'category3.jpg', 'Fruits'),
('4', 'category4.jpg', 'Légumes'),
('5', 'category5.jpg', 'Viandes'),
('6', 'category6.jpg', 'Poissons'),
('7', 'category7.jpg', 'Boulangerie'),
('8', 'category8.jpg', 'Produits laitiers'),
('9', 'category9.jpg', 'Épicerie sucrée'),
('10', 'category10.jpg', 'Épicerie salée'),
('11', 'category11.jpg', 'Boissons'),
('12', 'category12.jpg', 'Produits dentretien');

-- Sous-catégories pour la catégorie Épicerie (categoryid = 1)
INSERT INTO subcategories (id, categoryid, label) VALUES
('1', '1', 'Céréales'),
('2', '1', 'Légumineuses'),
('3', '1', 'Conserves'),
('4', '1', 'Sauces'),
('5', '1', 'Épices'),
('6', '2', 'Lait'),
('7', '2', 'Fromages'),
('8', '2', 'Yaourts'),
('9', '2', 'Beurre'),
('10', '2', 'Crème'),
('13', '3', 'Fruits à noyau'),
('14', '3', 'Agrumes'),
('15', '3', 'Baies'),
('16', '3', 'Fruits exotiques'),
('17', '3', 'Melons et pastèques'),
('18', '4', 'Légumes-feuilles'),
('19', '4', 'Légumes-racines'),
('20', '4', 'Légumes crucifères'),
('21', '4', 'Courges'),
('22', '4', 'Légumes exotiques'),
('23', '5', 'Bœuf'),
('24', '5', 'Poulet'),
('25', '5', 'Porc'),
('26', '5', 'Agneau'),
('27', '5', 'Produits à base de viande'),
('28', '6', 'Poissons eau douce'),
('29', '6', 'Poissons eau de mer'),
('30', '6', 'Fruits de mer'),
('31', '6', 'Poissons en conserve'),
('32', '6', 'Autres produits de la mer'),
('33', '7', 'Pain frais'),
('34', '7', 'Pâtisseries'),
('35', '7', 'Pain de grains entiers'),
('36', '7', 'Viennoiseries'),
('37', '7', 'Pain sans gluten'),
('38', '8', 'Lait'),
('39', '8', 'Fromages'),
('40', '8', 'Yaourts'),
('41', '8', 'Beurre'),
('42', '8', 'Crème'),
('43', '9', 'Confiseries'),
('44', '9', 'Chocolat'),
('45', '9', 'Biscuits'),
('46', '9', 'Gâteaux'),
('47', '9', 'Pâtisseries'),
('48', '10', 'Chips'),
('49', '10', 'Snacks salés'),
('', '10', 'Conserves salées'),
('51', '10', 'Céréales salées'),
('52', '10', 'Charcuterie'),
('53', '11', 'Eaux minérales'),
('54', '11', 'Sodas'),
('55', '11', 'Thé et Café'),
('56', '11', 'Jus de fruits'),
('57', '11', 'Bières'),
('58', '12', 'Nettoyants ménagers'),
('59', '12', 'Produits de lessive'),
('60', '12', 'Papier toilette'),
('61', '12', 'Produits pour vaisselle'),
('62', '12', 'Accessoires de nettoyage');


INSERT INTO products (id, imageurl, label, price, subcategoryid, supermarketid) VALUES
('1', 'product1.jpg', 'Avoine', 3, '1', '1'),
('2', 'product2.jpg', 'Lait demi-écrémé', 2,  '2', '2'),
('3', 'product3.jpg', 'Pain de blé entier', 2,  '1', '1'),
('4', 'product4.jpg', 'Lait écrémé', 2, '2', '1'),
('5', 'product5.jpg', 'Jus orange', 3,  '2', '1'),
('6', 'product6.jpg', 'Poulet rôti', 10,  '1', '1'),
('13', 'fruit1.jpg', 'Pommes', 2, '1', '1'),
('14', 'fruit2.jpg', 'Bananes', 2,  '1', '1'),
('83', 'product83.jpg', 'Cerises', 4,  '38', '1'),
('84', 'product84.jpg', 'Pêches', 3,  '38', '1'),
('85', 'product85.jpg', 'Prunes', 3,  '38', '1'),
('86', 'product86.jpg', 'Abricots', 4,  '38', '1'),
('87', 'product87.jpg', 'Nectarines', 3,  '38', '1'),
('88', 'product88.jpg', 'Oranges', 3,  '39', '1'),
('89', 'product89.jpg', 'Citrons', 2,  '39', '1'),
('90', 'product90.jpg', 'Pamplemousses', 3,  '39', '1'),
('91', 'product91.jpg', 'Mandarines', 3,  '39', '1'),
('92', 'product92.jpg', 'Limes', 2, '39', '1'),
('93', 'product93.jpg', 'Fraises', 4,  '40', '1'),
('94', 'product94.jpg', 'Mûres', 5,  '40', '1'),
('95', 'product95.jpg', 'Myrtilles', 4,  '40', '1'),
('96', 'product96.jpg', 'Framboises', 5,  '40', '1'),
('97', 'product97.jpg', 'Cassis', 4,  '40', '1'),
('123', 'product123.jpg', 'Haricots rouges en conserve', 2.5,  '2', '1'),
('124', 'product124.jpg', 'Pois chiches secs', 3,  '2', '1'),
('125', 'product125.jpg', 'Lentilles vertes', 2,  '2', '1'),
('126', 'product126.jpg', 'Haricots noirs en conserve', 2.5,  '2', '1'),
('127', 'product127.jpg', 'Pois cassés', 3,  '2', '1'),
('128', 'product128.jpg', 'Tomates en conserve', 2.5,  '3', '1'),
('129', 'product129.jpg', 'Maïs en conserve', 2,  '3', '1'),
('130', 'product130.jpg', 'Petits pois en conserve', 2.5,  '3', '1'),
('131', 'product131.jpg', 'Champignons en conserve', 2.5,  '3', '1'),
('132', 'product132.jpg', 'Sauce tomate en conserve', 3,  '3', '1'),
('133', 'product133.jpg', 'Sauce soja', 3,  '4', '1'),
('134', 'product134.jpg', 'Sauce barbecue', 2.5,  '4', '1'),
('135', 'product135.jpg', 'Ketchup', 2,  '4', '1'),
('136', 'product136.jpg', 'Mayonnaise', 2.5,  '4', '1'),
('137', 'product137.jpg', 'Sauce piquante', 3,  '4', '1'),
('138', 'product138.jpg', 'Poivre noir moulu', 2,  '5', '1'),
('139', 'product139.jpg', 'Sel de mer', 1.5,  '5', '1'),
('140', 'product140.jpg', 'Cumin moulu', 3,  '5', '1'),
('141', 'product141.jpg', 'Paprika', 2,  '5', '1'),
('142', 'product142.jpg', 'Cannelle en poudre', 2.5,  '5', '1');

-- Ajoutez 5 produits à chaque sous-catégorie au besoin


INSERT INTO grocerytemplatelist (id, createdat, label, userid) VALUES
('1', '2023-09-22 10:00:00', 'Liste de Courses Hebdomadaire', '1'),
('2', '2023-09-23 14:30:00', 'Liste de Courses Mensuelle', '2');

INSERT INTO intemplatelistproduct (id, grocerytemplatelistid, productid, wantedquantity) VALUES
('1', '1', '1', 8),
('2', '2', '2', 7);