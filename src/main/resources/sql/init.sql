
USE `demo`; $$
DROP function IF EXISTS `deg2rad`; $$

CREATE  FUNCTION `deg2rad`(deg double) RETURNS double
BEGIN 
return (deg * PI()/ 180.0);
END$$


DROP function IF EXISTS `rad2deg`; $$

CREATE  FUNCTION `rad2deg`(rad double) RETURNS double
BEGIN
 
return (rad * 180.0 / PI());

END$$



DROP function IF EXISTS `is_near_by`; $$


CREATE FUNCTION `is_near_by`(lat1 double,lng1 double, lat2 double, lng2 double) RETURNS double
BEGIN
 declare  theta double ;
 declare  dist double ;
 set  theta = lng1 - lng2 ;
 set  dist = sin(deg2rad(lat1)) * sin(deg2rad(lat2))
				+ cos(deg2rad(lat1)) * cos(deg2rad(lat2))
				* cos(deg2rad(theta));
set		dist = acos(dist);
set		dist = rad2deg(dist);
set		dist = dist * 60 * 1.1515;
Set	    dist = dist * 1.609344;
return (dist) ;

END$$


