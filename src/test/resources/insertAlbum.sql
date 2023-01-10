insert into albums(artiestId, labelId, naam, jaar, barcode, score) values
(
 (select id from artiesten where naam = 'test'),
 (select id from labels where naam = 'test'),
 'test', 1, 2, 3
);
insert into tracks(albumId, naam, tijd)
values((select id from albums where naam = 'test'), 'test', '00:00:01');