CREATE TABLE IF NOT EXISTS `characters` (
	`account_name` VARCHAR(45) NOT NULL DEFAULT '',
	`obj_Id` INT NOT NULL DEFAULT '0',
	`char_name` VARCHAR(35) CHARACTER SET UTF8 NOT NULL DEFAULT '',
	`face` TINYINT UNSIGNED DEFAULT NULL,
	`beautyFace` SMALLINT UNSIGNED DEFAULT NULL,
	`hairStyle` TINYINT UNSIGNED DEFAULT NULL,
	`beautyHairStyle` SMALLINT UNSIGNED DEFAULT NULL,
	`hairColor` TINYINT UNSIGNED DEFAULT NULL,
	`beautyHairColor` SMALLINT UNSIGNED DEFAULT NULL,
	`sex` BOOLEAN DEFAULT NULL,
	`x` mediumint DEFAULT NULL,
	`y` mediumint DEFAULT NULL,
	`z` mediumint DEFAULT NULL,
	`karma` INT DEFAULT NULL,
	`pvpkills` INT DEFAULT NULL,
	`pkkills` INT DEFAULT NULL,
	`clanid` INT DEFAULT NULL,
	`clan_attendance` TINYINT NOT NULL DEFAULT '0',
	`createtime` INT UNSIGNED NOT NULL DEFAULT '0',
	`deletetime` INT UNSIGNED NOT NULL DEFAULT '0',
	`title` VARCHAR(16) CHARACTER SET UTF8 DEFAULT NULL,
	`rec_have` TINYINT UNSIGNED NOT NULL DEFAULT '0',
	`rec_left` TINYINT UNSIGNED NOT NULL DEFAULT '20',
	`accesslevel` TINYINT DEFAULT NULL,
	`online` BOOLEAN DEFAULT NULL,
	`onlinetime` INT UNSIGNED NOT NULL DEFAULT '0',
	`lastAccess` INT UNSIGNED NOT NULL DEFAULT '0',
	`leaveclan`  INT UNSIGNED NOT NULL DEFAULT '0',
	`deleteclan` INT UNSIGNED NOT NULL DEFAULT '0',
	`nochannel` INT NOT NULL DEFAULT '0', -- not UNSIGNED, negative value means 'forever'
	`pledge_type` SMALLINT NOT NULL DEFAULT '-128',
	`pledge_rank` TINYINT UNSIGNED NOT NULL DEFAULT '0',
	`lvl_joined_academy` TINYINT UNSIGNED NOT NULL DEFAULT '0',
	`apprentice` INT UNSIGNED NOT NULL DEFAULT '0',
	`key_bindings` varbinary(8192) DEFAULT NULL,
	`pcBangPoints` INT NOT NULL DEFAULT '0',
	`fame` INT NOT NULL DEFAULT '0',
	`raid_points` INT NOT NULL DEFAULT '0',
	`bookmarks` INT UNSIGNED NOT NULL DEFAULT '0',
	`bot_rating` INT NOT NULL DEFAULT '0',
	`is_noble` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0',
	`used_world_chat_points` INT NOT NULL DEFAULT '0',
	`hide_head_accessories` TINYINT UNSIGNED NOT NULL DEFAULT '0',
	PRIMARY KEY (obj_Id),
	UNIQUE KEY `char_name` (`char_name`),
	KEY `account_name` (`account_name`),
	KEY `clanid` (`clanid`)
) ENGINE=MyISAM;