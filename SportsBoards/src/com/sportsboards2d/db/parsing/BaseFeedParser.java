package com.sportsboards2d.db.parsing;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */

public abstract class BaseFeedParser implements FeedParser{
	
	//XML tag names
	
	//formation tags
	static final String FORMS = "forms";
	static final String FORM = "form";
	static final String FNAME = "name";
	static final String BALL = "ball";
	static final String PENTRY = "pEntry";
	
	static final String PID = "pID";

	static final String PTEAM = "team";
	static final String PCOORDS = "coords";
	
	//config tags
	static final String CONFIG = "config";
	static final String LINE_ENABLED = "line_enabled";
	static final String LOAD_LAST = "last_loaded";
	static final String PLAYER_SIZE = "player_size";
	static final String DEFAULT = "default_sport";
	
	//player tags
	static final String PLAYERS = "players";
	static final String PLAYER = "player";
	static final String JNUM = "jNum";
	static final String TYPE = "type";
	static final String PNAME = "pName";

	
}