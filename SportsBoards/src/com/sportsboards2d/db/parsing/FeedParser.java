package com.sportsboards2d.db.parsing;

import java.io.InputStream;
import java.util.List;

import com.sportsboards2d.db.objects.FormationObject;
import com.sportsboards2d.db.objects.PlayerInfo;

/**
 * Coded by Nathan King
 */

/**
 * Copyright 2011 5807400 Manitoba Inc. All rights reserved.
 */

public interface FeedParser{
	List<FormationObject> parseFormation(InputStream input);
	List<PlayerInfo> parsePlayers(InputStream input);
}