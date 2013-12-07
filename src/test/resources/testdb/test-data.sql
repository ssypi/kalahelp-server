insert into NEWS (
                     ID,
                     CONTENT,
                     WRITTEN_BY,
                     DATE)
       values (
              1,
              'Kalamiehen testi_uutinen',
              'Kalamies',
              '2013-10-18 00:42:59');
insert into NEWS (
                     ID,
                     CONTENT,
                     WRITTEN_BY,
                     DATE) values (
                                  2,
                                  'Kalakala',
                                  'Kalakaveri',
                                  '2013-10-19 05:35:34');
insert into USER (
                    USERNAME,
                    PASSWORD,
                    SALT) values (
              'kalamies',
              X'1065B4684E77D42CE1A84DE7AB84CDA81EF8F4D8' , X'77CF58269CBAAEAF');

insert into USER (
  USERNAME,
  PASSWORD,
  SALT) values (
  'kalamies2',
  X'1065B4684E77D42CE1A84DE7AB84CDA81EF8F4D8' , X'77CF58269CBAAEAF');

INSERT INTO TICKET (TICKET_NUMBER, STATUS, CATEGORY, SUBJECT, SENDER_NAME, SENDER_EMAIL, MESSAGE, DATE, REPLY_BY, REPLY_MESSAGE, REPLY_DATE) VALUES (1, 'New', 'Other', 'kala', 'kalamies', 'kala@kalamail.com', 'kala oli kalamies', '2011-11-30 21:11:23', null, '', '2013-10-09 03:12:46');
INSERT INTO CATEGORY (NAME) VALUES ('kalamiehen gatogory');