
    create table T_APPEARANCE (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        dicesResult integer,
        primary key (ID)
    );

    create table T_BACKGROUND (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_BACKGROUND_CATEGORIES (
        Background_ID bigint not null,
        categories varchar(255)
    );

    create table T_BACKGROUND_CHARACTERISTICS_UPDATES (
        T_BACKGROUND_ID bigint not null,
        characteristicsUpdates_ID bigint not null,
        roll_index integer not null,
        primary key (T_BACKGROUND_ID, roll_index)
    );

    create table T_BACKGROUND_LANGUAGE_RANKS (
        Background_ID bigint not null,
        languageRanks integer,
        languageRanks_KEY varchar(255),
        primary key (Background_ID, languageRanks_KEY)
    );

    create table T_BACKGROUND_OPTIONAL_RACE_LANGUAGES (
        Background_ID bigint not null,
        optionalRaceLanguageSelection varchar(255),
        raceLanguageIndex integer not null,
        primary key (Background_ID, raceLanguageIndex)
    );

    create table T_BACKGROUND_SKILLS (
        Background_ID bigint not null,
        skills varchar(255)
    );

    create table T_CATEGORY_COST (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        categoryCostId varchar(255),
        primary key (ID)
    );

    create table T_CHARACTERISTIC_ROLL_GROUP (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        characteristicAbbreviature varchar(255),
        characteristicPotentialValue integer,
        characteristicTemporalValue integer,
        roll_ID bigint,
        primary key (ID)
    );

    create table T_CHARACTERPLAYER (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        characteristicsConfirmed bit not null,
        characteristicsTemporalTotalPoints integer,
        cultureName varchar(255),
        historyText varchar(255),
        name varchar(255),
        professionName varchar(255),
        raceName varchar(255),
        sex varchar(255),
        version varchar(255),
        appearance_ID bigint,
        backgroundId bigint,
        configuration bigint,
        cultureDecisionsId bigint,
        insertedDataId bigint,
        professionDecisionsId bigint,
        professionalRealmId bigint,
        primary key (ID)
    );

    create table T_CHARACTERPLAYER_INITIAL_TEMPORAL_VALUES (
        CharacterPlayer_ID bigint not null,
        characteristicsInitialTemporalValues integer,
        characteristicsInitialTemporalValues_KEY integer,
        primary key (CharacterPlayer_ID, characteristicsInitialTemporalValues_KEY)
    );

    create table T_CHARACTERPLAYER_LEVEL_UP (
        T_CHARACTERPLAYER_ID bigint not null,
        levelUps_ID bigint not null,
        level_index integer not null,
        primary key (T_CHARACTERPLAYER_ID, level_index)
    );

    create table T_CHARACTERPLAYER_MAGIC_ITEMS (
        T_CHARACTERPLAYER_ID bigint not null,
        magicItems_ID bigint not null
    );

    create table T_CHARACTERPLAYER_PERKS_DECISIONS (
        T_CHARACTERPLAYER_ID bigint not null,
        perkDecisions_ID bigint not null,
        perkDecisions_KEY varchar(255),
        primary key (T_CHARACTERPLAYER_ID, perkDecisions_KEY)
    );

    create table T_CHARACTERPLAYER_POTENTIAL_VALUES (
        CharacterPlayer_ID bigint not null,
        characteristicsPotentialValues integer,
        characteristicsPotentialValues_KEY integer,
        primary key (CharacterPlayer_ID, characteristicsPotentialValues_KEY)
    );

    create table T_CHARACTERPLAYER_SELECTED_PERKS (
        T_CHARACTERPLAYER_ID bigint not null,
        selectedPerks_ID bigint not null
    );

    create table T_CHARACTERPLAYER_SKILLS_ENABLED (
        CharacterPlayer_ID bigint not null,
        enabledSkill varchar(255),
        enabledSkill_KEY varchar(255),
        primary key (CharacterPlayer_ID, enabledSkill_KEY)
    );

    create table T_CHARACTERPLAYER_TEMPORAL_CHARACTERISTICS_ROLLS (
        T_CHARACTERPLAYER_ID bigint not null,
        characteristicsTemporalUpdatesRolls_ID bigint not null,
        characteristicsTemporalUpdatesRolls_KEY integer,
        primary key (T_CHARACTERPLAYER_ID, characteristicsTemporalUpdatesRolls_KEY)
    );

    create table T_CHARACTER_CONFIGURATION (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        chiPowersAllowed bit not null,
        darkSpellsAsBasic bit not null,
        fireArmsActivated bit not null,
        handWrittingFont bit not null,
        otherRealmsTrainingSpells bit not null,
        perksCostHistoryPoints bit not null,
        sortPdfSkills bit not null,
        primary key (ID)
    );

    create table T_CULTUREDECISIONS (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_CULTURE_ADOLESCENCE_RANKS (
        CultureDecisions_ID bigint not null,
        adolescenceRanks integer,
        adolescenceRanks_KEY varchar(255),
        primary key (CultureDecisions_ID, adolescenceRanks_KEY)
    );

    create table T_CULTURE_HOBBY_RANKS (
        CultureDecisions_ID bigint not null,
        hobbyRanks integer,
        hobbyRanks_KEY varchar(255),
        primary key (CultureDecisions_ID, hobbyRanks_KEY)
    );

    create table T_CULTURE_LANGUAGE_RANKS (
        CultureDecisions_ID bigint not null,
        languageRanks integer,
        languageRanks_KEY varchar(255),
        primary key (CultureDecisions_ID, languageRanks_KEY)
    );

    create table T_CULTURE_OPTIONAL_CATEGORIES (
        CultureDecisions_ID bigint not null,
        adolescenceCategoriesSelected varchar(255)
    );

    create table T_CULTURE_OPTIONAL_CULTURE_LANGUAGES (
        CultureDecisions_ID bigint not null,
        optionalCulturalLanguageSelection varchar(255),
        raceLanguageIndex integer not null,
        primary key (CultureDecisions_ID, raceLanguageIndex)
    );

    create table T_CULTURE_OPTIONAL_RACE_LANGUAGES (
        CultureDecisions_ID bigint not null,
        optionalRaceLanguageSelection varchar(255),
        raceLanguageIndex integer not null,
        primary key (CultureDecisions_ID, raceLanguageIndex)
    );

    create table T_CULTURE_SKILL_RANKS (
        CultureDecisions_ID bigint not null,
        skillRanks integer,
        skillRanks_KEY varchar(255),
        primary key (CultureDecisions_ID, skillRanks_KEY)
    );

    create table T_CULTURE_SPELL_RANKS (
        CultureDecisions_ID bigint not null,
        spellRanks integer,
        spellRanks_KEY varchar(255),
        primary key (CultureDecisions_ID, spellRanks_KEY)
    );

    create table T_FAVOURITE_SKILLS (
        LevelUp_ID bigint not null,
        favouriteSkills varchar(255)
    );

    create table T_INSERTED_CATEGORIES_RANKS (
        InsertedData_ID bigint not null,
        categoriesRanksModification integer,
        categoriesRanksModification_KEY varchar(255),
        primary key (InsertedData_ID, categoriesRanksModification_KEY)
    );

    create table T_INSERTED_DATA (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        createdAtLevel integer,
        insertedLevels integer,
        primary key (ID)
    );

    create table T_INSERTED_GENERALIZED_SKILLS (
        InsertedData_ID bigint not null,
        generalizedSkillsAdded varchar(255)
    );

    create table T_INSERTED_POTENTIAL_VALUES (
        InsertedData_ID bigint not null,
        characteristicsPotentialValuesModification integer,
        characteristicsPotentialValuesModification_KEY integer,
        primary key (InsertedData_ID, characteristicsPotentialValuesModification_KEY)
    );

    create table T_INSERTED_SKILLS_RANKS (
        InsertedData_ID bigint not null,
        skillsRanksModification integer,
        skillsRanksModification_KEY varchar(255),
        primary key (InsertedData_ID, skillsRanksModification_KEY)
    );

    create table T_INSERTED_SKILL_SPECIALIZATIONS (
        InsertedData_ID bigint not null,
        skillSpecializationsAdded varchar(255)
    );

    create table T_INSERTED_TEMPORAL_VALUES (
        InsertedData_ID bigint not null,
        characteristicsTemporalValuesModification integer,
        characteristicsTemporalValuesModification_KEY integer,
        primary key (InsertedData_ID, characteristicsTemporalValuesModification_KEY)
    );

    create table T_INSERTED_TRAININGS_ADQUIRED (
        InsertedData_ID bigint not null,
        trainingsAdded varchar(255)
    );

    create table T_INSERTED_TRAINING_DECISIONS (
        T_INSERTED_DATA_ID bigint not null,
        trainingDecisions_ID bigint not null,
        trainingDecisions_KEY varchar(255),
        primary key (T_INSERTED_DATA_ID, trainingDecisions_KEY)
    );

    create table T_LEVELUP (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_LEVELUP_CATEGORIES_RANKS (
        LevelUp_ID bigint not null,
        categoriesRanks integer,
        categoriesRanks_KEY varchar(255),
        primary key (LevelUp_ID, categoriesRanks_KEY)
    );

    create table T_LEVELUP_GENERALIZED_SKILLS (
        LevelUp_ID bigint not null,
        generalizedSkills varchar(255)
    );

    create table T_LEVELUP_SKILLS_RANKS (
        LevelUp_ID bigint not null,
        skillsRanks integer,
        skillsRanks_KEY varchar(255),
        primary key (LevelUp_ID, skillsRanks_KEY)
    );

    create table T_LEVEL_UP_CHARACTERISTICS_UPDATES (
        T_LEVELUP_ID bigint not null,
        characteristicsUpdates_ID bigint not null
    );

    create table T_LEVEL_UP_SKILL_SPECIALIZATIONS (
        LevelUp_ID bigint not null,
        skillSpecializations varchar(255)
    );

    create table T_LEVEL_UP_SPELLS_UPDATED (
        LevelUp_ID bigint not null,
        spellsUpdated varchar(255)
    );

    create table T_LEVEL_UP_TRAININGS_ADQUIRED (
        LevelUp_ID bigint not null,
        trainings varchar(255)
    );

    create table T_LEVEL_UP_TRAINING_DECISIONS (
        T_LEVELUP_ID bigint not null,
        trainingDecisions_ID bigint not null,
        trainingDecisions_KEY varchar(255),
        primary key (T_LEVELUP_ID, trainingDecisions_KEY)
    );

    create table T_MAGIC_OBJECT (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        description varchar(255),
        name varchar(255),
        primary key (ID)
    );

    create table T_MAGIC_OBJECT_BONUS (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        bonus integer not null,
        bonusName varchar(255),
        type varchar(255),
        primary key (ID)
    );

    create table T_MAGIC_OBJECT_T_MAGIC_OBJECT_BONUS (
        T_MAGIC_OBJECT_ID bigint not null,
        bonus_ID bigint not null
    );

    create table T_PERKS (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        cost integer,
        name varchar(255),
        weakness_ID bigint,
        primary key (ID)
    );

    create table T_PERKS_DECISION (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_PERKS_DECISION_CATEGORY_BONUS_CHOSEN (
        PerkDecision_ID bigint not null,
        categoriesBonusChosen varchar(255)
    );

    create table T_PERKS_DECISION_CATEGORY_RANKS_CHOSEN (
        PerkDecision_ID bigint not null,
        categoriesRanksChosen varchar(255)
    );

    create table T_PERKS_DECISION_COMMON_SKILLS_CHOSEN (
        PerkDecision_ID bigint not null,
        commonSkillsChosen varchar(255)
    );

    create table T_PERKS_DECISION_SKILLS_BONUS_CHOSEN (
        PerkDecision_ID bigint not null,
        skillsBonusChosen varchar(255)
    );

    create table T_PERKS_DECISION_SKILLS_RANKS_CHOSEN (
        PerkDecision_ID bigint not null,
        skillsRanksChosen varchar(255)
    );

    create table T_PROFESSIONS_REALMS_MAGIC (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_PROFESSION_COMMON_SKILLS_CHOSEN (
        ProfessionDecisions_ID bigint not null,
        commonSkillsChosen varchar(255)
    );

    create table T_PROFESSION_DECISIONS (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_PROFESSION_PROFESSIONAL_SKILLS_CHOSEN (
        ProfessionDecisions_ID bigint not null,
        professionalSkillsChosen varchar(255)
    );

    create table T_PROFESSION_REALMS_OF_MAGIC_AVAILABLE (
        ProfessionalRealmsOfMagicOptions_ID bigint not null,
        magicRealmsAvailable integer
    );

    create table T_PROFESSION_RESTRICTED_SKILLS_CHOSEN (
        ProfessionDecisions_ID bigint not null,
        restrictedSkillsChosen varchar(255)
    );

    create table T_PROFESSION_WEAPON_COST_CHOSEN (
        T_PROFESSION_DECISIONS_ID bigint not null,
        weaponsCost_ID bigint not null,
        weaponsCost_KEY varchar(255),
        primary key (T_PROFESSION_DECISIONS_ID, weaponsCost_KEY)
    );

    create table T_RANK_COSTS (
        CategoryCost_ID bigint not null,
        rankCost integer
    );

    create table T_ROLL (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        firstDice integer,
        secondDice integer,
        primary key (ID)
    );

    create table T_ROLL_GROUP (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        characteristicAbbreviature varchar(255),
        primary key (ID)
    );

    create table T_ROLL_LIST (
        T_ROLL_GROUP_ID bigint not null,
        rolls_ID bigint not null,
        roll_index integer not null,
        primary key (T_ROLL_GROUP_ID, roll_index)
    );

    create table T_TRAINING_CATEGORIES (
        TrainingCategoriesSelected_ID bigint not null,
        categories varchar(255)
    );

    create table T_TRAINING_CATEGORIES_SELECTED (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_TRAINING_CHARACTERISTICS_UPDATES (
        T_TRAINING_DECISION_ID bigint not null,
        characteristicsUpdates_ID bigint not null
    );

    create table T_TRAINING_DECISION (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_TRAINING_DECISION_CATEGORY_SELECTED (
        T_TRAINING_DECISION_ID bigint not null,
        categoriesSelected_ID bigint not null,
        categoriesSelected_KEY integer,
        primary key (T_TRAINING_DECISION_ID, categoriesSelected_KEY)
    );

    create table T_TRAINING_DECISION_COMMON_SKILLS (
        TrainingDecision_ID bigint not null,
        commonSkillsChosen varchar(255)
    );

    create table T_TRAINING_DECISION_PROFESSIONAL_SKILLS (
        TrainingDecision_ID bigint not null,
        professionalSkillsChosen varchar(255)
    );

    create table T_TRAINING_DECISION_RESTRICTED_SKILLS (
        TrainingDecision_ID bigint not null,
        restrictedSkillsChosen varchar(255)
    );

    create table T_TRAINING_DECISION_SKILLS_SELECTED (
        T_TRAINING_DECISION_ID bigint not null,
        skillsSelected_ID bigint not null,
        skillsSelected_KEY integer,
        primary key (T_TRAINING_DECISION_ID, skillsSelected_KEY)
    );

    create table T_TRAINING_MAGIC_ITEMS (
        T_TRAINING_DECISION_ID bigint not null,
        magicItems_ID bigint not null
    );

    create table T_TRAINING_OBJECT (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        bonus integer not null,
        description varchar(255),
        name varchar(255),
        probability integer not null,
        skill varchar(255),
        type varchar(255),
        primary key (ID)
    );

    create table T_TRAINING_OBJECTS (
        T_TRAINING_DECISION_ID bigint not null,
        equipment_ID bigint not null
    );

    create table T_TRAINING_SKILLS_RANKS_PER_SKILL (
        TrainingSkillsSelected_ID bigint not null,
        skillsRanks integer,
        skillsRanks_KEY varchar(255),
        primary key (TrainingSkillsSelected_ID, skillsRanks_KEY)
    );

    create table T_TRAINING_SKILLS_SELECTED (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    alter table T_APPEARANCE 
        add constraint UK_hlcvnifaglalnf0dpfbcak1u8 unique (ID);

    alter table T_APPEARANCE 
        add constraint UK_fvvhnk8c7ii3jvbmo5b6fg5un unique (comparationId);

    alter table T_BACKGROUND 
        add constraint UK_tcyp241rqmolym4d1a1h575di unique (ID);

    alter table T_BACKGROUND 
        add constraint UK_kg8ehmc8pkdlemaya4qgxr2g3 unique (comparationId);

    alter table T_BACKGROUND_CHARACTERISTICS_UPDATES 
        add constraint UK_4sttp2pdvsngqkhbigpb2l597 unique (characteristicsUpdates_ID);

    alter table T_CATEGORY_COST 
        add constraint UK_fcmfkigake2hqq44gyge781xw unique (ID);

    alter table T_CATEGORY_COST 
        add constraint UK_8jn0hgs0qu2147j9ge5siu0qq unique (comparationId);

    alter table T_CHARACTERISTIC_ROLL_GROUP 
        add constraint UK_g823n2nv2eyimog2a4dpckhbd unique (ID);

    alter table T_CHARACTERISTIC_ROLL_GROUP 
        add constraint UK_76e7clc7lyv03qmf0d6lvgyi2 unique (comparationId);

    alter table T_CHARACTERPLAYER 
        add constraint UK_conobsyh7ebxy51m6q8ieunak unique (ID);

    alter table T_CHARACTERPLAYER 
        add constraint UK_goif6qdq0k03683pf598f958 unique (comparationId);

    alter table T_CHARACTERPLAYER_LEVEL_UP 
        add constraint UK_l8mhtklrekoduk3xl5a85ll55 unique (levelUps_ID);

    alter table T_CHARACTERPLAYER_MAGIC_ITEMS 
        add constraint UK_kgwp84wlntv117f3rbbw2b1a0 unique (magicItems_ID);

    alter table T_CHARACTERPLAYER_PERKS_DECISIONS 
        add constraint UK_2rei81rlw78vo1pss3lmg7lsq unique (perkDecisions_ID);

    alter table T_CHARACTERPLAYER_SELECTED_PERKS 
        add constraint UK_c36v4kn7rg6y2oeqamj1vftp6 unique (selectedPerks_ID);

    alter table T_CHARACTERPLAYER_TEMPORAL_CHARACTERISTICS_ROLLS 
        add constraint UK_rsdhy70yoehafjvur7w9b2o66 unique (characteristicsTemporalUpdatesRolls_ID);

    alter table T_CHARACTER_CONFIGURATION 
        add constraint UK_j71g2kcogvhhr78fdk4hdsilt unique (ID);

    alter table T_CHARACTER_CONFIGURATION 
        add constraint UK_h49dq3fm78h7isy08sayox779 unique (comparationId);

    alter table T_CULTUREDECISIONS 
        add constraint UK_blq96e017u1trnpdefgr091nl unique (ID);

    alter table T_CULTUREDECISIONS 
        add constraint UK_i4vxctno4axrv6clm66mmrngb unique (comparationId);

    alter table T_INSERTED_DATA 
        add constraint UK_h99y61rxnrq69c3nx0n2ko4m3 unique (ID);

    alter table T_INSERTED_DATA 
        add constraint UK_p65fw3bsg7kkp8t68dncqwxni unique (comparationId);

    alter table T_INSERTED_TRAINING_DECISIONS 
        add constraint UK_ahxpf8sitwcloo3ha8nft78k3 unique (trainingDecisions_ID);

    alter table T_LEVELUP 
        add constraint UK_e9y7b2htj85ls5jccr8dscoiv unique (ID);

    alter table T_LEVELUP 
        add constraint UK_s260ukdop7lmbms1wy8vru5mp unique (comparationId);

    alter table T_LEVEL_UP_CHARACTERISTICS_UPDATES 
        add constraint UK_mayw23itpoug630tuos9ycyjx unique (characteristicsUpdates_ID);

    alter table T_LEVEL_UP_TRAINING_DECISIONS 
        add constraint UK_e55t66sktxlv50j5s7jk3j7x1 unique (trainingDecisions_ID);

    alter table T_MAGIC_OBJECT 
        add constraint UK_35phtj8okwti1qwsijrvr9t9q unique (ID);

    alter table T_MAGIC_OBJECT 
        add constraint UK_sj0a2io0jkgf480bygxoklivy unique (comparationId);

    alter table T_MAGIC_OBJECT_BONUS 
        add constraint UK_mhw45ibhr60ep1mxs4mwh5vsb unique (ID);

    alter table T_MAGIC_OBJECT_BONUS 
        add constraint UK_asx9ocfy2qd86t7q2meimfjrc unique (comparationId);

    alter table T_MAGIC_OBJECT_T_MAGIC_OBJECT_BONUS 
        add constraint UK_h8abfb792vwsl7s8bk57h6nfk unique (bonus_ID);

    alter table T_PERKS 
        add constraint UK_poul3t4t5tpjoxm9csig34aml unique (ID);

    alter table T_PERKS 
        add constraint UK_a0gfx9ounae0o9sm9x40i5rdg unique (comparationId);

    alter table T_PERKS_DECISION 
        add constraint UK_lom2ng3ig68tkf86di7wwjk0s unique (ID);

    alter table T_PERKS_DECISION 
        add constraint UK_s4uwcg9f92ddv6obvlt98shqo unique (comparationId);

    alter table T_PROFESSIONS_REALMS_MAGIC 
        add constraint UK_b8a8hn5ijfh46oagm4d2um6qa unique (ID);

    alter table T_PROFESSIONS_REALMS_MAGIC 
        add constraint UK_8nvq9erlbw339m1kj0gisa025 unique (comparationId);

    alter table T_PROFESSION_DECISIONS 
        add constraint UK_li3y57bhuj92ukf2ys827be5j unique (ID);

    alter table T_PROFESSION_DECISIONS 
        add constraint UK_16qm0bkl5f4pi9qw4bf8x9hra unique (comparationId);

    alter table T_PROFESSION_WEAPON_COST_CHOSEN 
        add constraint UK_hyu9pvkqjkxlo6ldjah1i6mkk unique (weaponsCost_ID);

    alter table T_ROLL 
        add constraint UK_9r4ehgb8fn2dn3d3sd8xvd4fd unique (ID);

    alter table T_ROLL 
        add constraint UK_ddfjd9r5evwwehksmtwe8cay1 unique (comparationId);

    alter table T_ROLL_GROUP 
        add constraint UK_ec1a2pas67gu5pl4tfelsan0r unique (ID);

    alter table T_ROLL_GROUP 
        add constraint UK_1tsw2ugbypulgt0xywmak2llm unique (comparationId);

    alter table T_TRAINING_CATEGORIES_SELECTED 
        add constraint UK_cjlsw6faxbj52bjt31aow627g unique (ID);

    alter table T_TRAINING_CATEGORIES_SELECTED 
        add constraint UK_5erag6etowycwtuhypccyvl4o unique (comparationId);

    alter table T_TRAINING_CHARACTERISTICS_UPDATES 
        add constraint UK_fnar1y6mb6imcc4ik6n3h72y6 unique (characteristicsUpdates_ID);

    alter table T_TRAINING_DECISION 
        add constraint UK_ooc3nxaim40wa5pawjtvkaeug unique (ID);

    alter table T_TRAINING_DECISION 
        add constraint UK_1fy563xslnew4m359jnvmyghf unique (comparationId);

    alter table T_TRAINING_DECISION_CATEGORY_SELECTED 
        add constraint UK_dcgwqauprl9jdgym1rwfd8i3v unique (categoriesSelected_ID);

    alter table T_TRAINING_DECISION_SKILLS_SELECTED 
        add constraint UK_5nc8p7y8l2d6jwmdevwemmrtx unique (skillsSelected_ID);

    alter table T_TRAINING_MAGIC_ITEMS 
        add constraint UK_onpxi6hsdqh3um18at3hno1tv unique (magicItems_ID);

    alter table T_TRAINING_OBJECT 
        add constraint UK_6qjbapnqh4clnepqgn6llqq36 unique (ID);

    alter table T_TRAINING_OBJECT 
        add constraint UK_7dphteoforn6hgtxxd3ft66bq unique (comparationId);

    alter table T_TRAINING_OBJECTS 
        add constraint UK_7ixq2spabhxg6hap4um27sj1y unique (equipment_ID);

    alter table T_TRAINING_SKILLS_SELECTED 
        add constraint UK_8t3mjh043dknq4icvg66qjl9l unique (ID);

    alter table T_TRAINING_SKILLS_SELECTED 
        add constraint UK_s4duauy8ccp5mc0938bw027dl unique (comparationId);

    alter table T_BACKGROUND_CATEGORIES 
        add constraint FK_qfry0khx7puobhvanxbum9lob 
        foreign key (Background_ID) 
        references T_BACKGROUND (ID);

    alter table T_BACKGROUND_CHARACTERISTICS_UPDATES 
        add constraint FK_4sttp2pdvsngqkhbigpb2l597 
        foreign key (characteristicsUpdates_ID) 
        references T_CHARACTERISTIC_ROLL_GROUP (ID);

    alter table T_BACKGROUND_CHARACTERISTICS_UPDATES 
        add constraint FK_83gvbe7gn6lw2trqk9a4i8w6o 
        foreign key (T_BACKGROUND_ID) 
        references T_BACKGROUND (ID);

    alter table T_BACKGROUND_LANGUAGE_RANKS 
        add constraint FK_h9p5rr5wpryrt7ofvfs5nov8 
        foreign key (Background_ID) 
        references T_BACKGROUND (ID);

    alter table T_BACKGROUND_OPTIONAL_RACE_LANGUAGES 
        add constraint FK_7l4gp0h23os7gyjylysu73phq 
        foreign key (Background_ID) 
        references T_BACKGROUND (ID);

    alter table T_BACKGROUND_SKILLS 
        add constraint FK_swltv618wty5mj3e6mcrs0xav 
        foreign key (Background_ID) 
        references T_BACKGROUND (ID);

    alter table T_CHARACTERISTIC_ROLL_GROUP 
        add constraint FK_jb6k15e74i40yqa7r969c1qwx 
        foreign key (roll_ID) 
        references T_ROLL (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_85pdo40kki5hlykhibmdj10xl 
        foreign key (appearance_ID) 
        references T_APPEARANCE (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_os1kt82jl37c7r6o4fnurp3n2 
        foreign key (backgroundId) 
        references T_BACKGROUND (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_hxi005f422otrkhcf92puorkc 
        foreign key (configuration) 
        references T_CHARACTER_CONFIGURATION (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_a6if2gfydatge62dg8j1pgy5w 
        foreign key (cultureDecisionsId) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_sf6x1b6j7k1j0k5u6l2rmdrje 
        foreign key (insertedDataId) 
        references T_INSERTED_DATA (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_h988k07hk2n60qnuqd21dakou 
        foreign key (professionDecisionsId) 
        references T_PROFESSION_DECISIONS (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_oj2d58brr5h1doqyb52ywkmew 
        foreign key (professionalRealmId) 
        references T_PROFESSIONS_REALMS_MAGIC (ID);

    alter table T_CHARACTERPLAYER_INITIAL_TEMPORAL_VALUES 
        add constraint FK_s4do2so1bmthu4487n53g901n 
        foreign key (CharacterPlayer_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_LEVEL_UP 
        add constraint FK_l8mhtklrekoduk3xl5a85ll55 
        foreign key (levelUps_ID) 
        references T_LEVELUP (ID);

    alter table T_CHARACTERPLAYER_LEVEL_UP 
        add constraint FK_f486l1torg28a6w7chhh1ktbg 
        foreign key (T_CHARACTERPLAYER_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_MAGIC_ITEMS 
        add constraint FK_kgwp84wlntv117f3rbbw2b1a0 
        foreign key (magicItems_ID) 
        references T_MAGIC_OBJECT (ID);

    alter table T_CHARACTERPLAYER_MAGIC_ITEMS 
        add constraint FK_syhh9dkh9kfkx517xqjdwjw9o 
        foreign key (T_CHARACTERPLAYER_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_PERKS_DECISIONS 
        add constraint FK_2rei81rlw78vo1pss3lmg7lsq 
        foreign key (perkDecisions_ID) 
        references T_PERKS_DECISION (ID);

    alter table T_CHARACTERPLAYER_PERKS_DECISIONS 
        add constraint FK_kxce5xahoaxhwil8fwguaqcjh 
        foreign key (T_CHARACTERPLAYER_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_POTENTIAL_VALUES 
        add constraint FK_n87ng61k8wj5d1sfq91jcvpt4 
        foreign key (CharacterPlayer_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_SELECTED_PERKS 
        add constraint FK_c36v4kn7rg6y2oeqamj1vftp6 
        foreign key (selectedPerks_ID) 
        references T_PERKS (ID);

    alter table T_CHARACTERPLAYER_SELECTED_PERKS 
        add constraint FK_1i4nykoq34g1roy464q5mi2qe 
        foreign key (T_CHARACTERPLAYER_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_SKILLS_ENABLED 
        add constraint FK_9ntkfbhey7gcb35m9l02pw7pe 
        foreign key (CharacterPlayer_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_TEMPORAL_CHARACTERISTICS_ROLLS 
        add constraint FK_rsdhy70yoehafjvur7w9b2o66 
        foreign key (characteristicsTemporalUpdatesRolls_ID) 
        references T_ROLL_GROUP (ID);

    alter table T_CHARACTERPLAYER_TEMPORAL_CHARACTERISTICS_ROLLS 
        add constraint FK_cwbxkyfe4g8d93yl6eruxvhyf 
        foreign key (T_CHARACTERPLAYER_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CULTURE_ADOLESCENCE_RANKS 
        add constraint FK_3k2mehjuwiqvd8ip0nmuerrsv 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CULTURE_HOBBY_RANKS 
        add constraint FK_3qei47al2s0hxq2w1uo0ml0ai 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CULTURE_LANGUAGE_RANKS 
        add constraint FK_f0iotbk7g37ft2eid62prpsct 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CULTURE_OPTIONAL_CATEGORIES 
        add constraint FK_5fdlhloq13167thk8nll68m3p 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CULTURE_OPTIONAL_CULTURE_LANGUAGES 
        add constraint FK_r3n5fqnifsedv0i1ocht6qwbq 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CULTURE_OPTIONAL_RACE_LANGUAGES 
        add constraint FK_7j7i6y8sw7x6c3txfxtxuufev 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CULTURE_SKILL_RANKS 
        add constraint FK_1y8qbfl0yltrrt62vt7gmo3be 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CULTURE_SPELL_RANKS 
        add constraint FK_qf6tc6n7nslys2rhnhe94cgw 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_FAVOURITE_SKILLS 
        add constraint FK_e4sag5ym4ve2sequ1a09uwfdb 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_INSERTED_CATEGORIES_RANKS 
        add constraint FK_oty3rpochepuhxys6dr4k0586 
        foreign key (InsertedData_ID) 
        references T_INSERTED_DATA (ID);

    alter table T_INSERTED_GENERALIZED_SKILLS 
        add constraint FK_de947s4ll2sfaca0af9o4i1dv 
        foreign key (InsertedData_ID) 
        references T_INSERTED_DATA (ID);

    alter table T_INSERTED_POTENTIAL_VALUES 
        add constraint FK_q028dnc1p1awjuln1vod526sb 
        foreign key (InsertedData_ID) 
        references T_INSERTED_DATA (ID);

    alter table T_INSERTED_SKILLS_RANKS 
        add constraint FK_9u758eu0qi88tds6vgd0j1wqk 
        foreign key (InsertedData_ID) 
        references T_INSERTED_DATA (ID);

    alter table T_INSERTED_SKILL_SPECIALIZATIONS 
        add constraint FK_l4uxmn4qko3tniee523n7wywa 
        foreign key (InsertedData_ID) 
        references T_INSERTED_DATA (ID);

    alter table T_INSERTED_TEMPORAL_VALUES 
        add constraint FK_ghrqijnv3wbp2gdf4x8lpqn0y 
        foreign key (InsertedData_ID) 
        references T_INSERTED_DATA (ID);

    alter table T_INSERTED_TRAININGS_ADQUIRED 
        add constraint FK_jdgf6d09vevygqghkkosaex2h 
        foreign key (InsertedData_ID) 
        references T_INSERTED_DATA (ID);

    alter table T_INSERTED_TRAINING_DECISIONS 
        add constraint FK_ahxpf8sitwcloo3ha8nft78k3 
        foreign key (trainingDecisions_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_INSERTED_TRAINING_DECISIONS 
        add constraint FK_op0s59o6argyn0qqylbm4m0rk 
        foreign key (T_INSERTED_DATA_ID) 
        references T_INSERTED_DATA (ID);

    alter table T_LEVELUP_CATEGORIES_RANKS 
        add constraint FK_qfiu7ugdjl7amg1s25i9j3byj 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVELUP_GENERALIZED_SKILLS 
        add constraint FK_6n8yhtwyywo6dd85q0w4f6hf3 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVELUP_SKILLS_RANKS 
        add constraint FK_pqvlidwgdyyxcup7fwk8pohs 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVEL_UP_CHARACTERISTICS_UPDATES 
        add constraint FK_mayw23itpoug630tuos9ycyjx 
        foreign key (characteristicsUpdates_ID) 
        references T_CHARACTERISTIC_ROLL_GROUP (ID);

    alter table T_LEVEL_UP_CHARACTERISTICS_UPDATES 
        add constraint FK_rvudfsdm7kvogeki9oi10twqm 
        foreign key (T_LEVELUP_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVEL_UP_SKILL_SPECIALIZATIONS 
        add constraint FK_67y497yssq86931g5r7rqpjyp 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVEL_UP_SPELLS_UPDATED 
        add constraint FK_kf3828eg2e2cxfb2jviphqu3e 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVEL_UP_TRAININGS_ADQUIRED 
        add constraint FK_qulxx88l837mgli4re9byjayc 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVEL_UP_TRAINING_DECISIONS 
        add constraint FK_e55t66sktxlv50j5s7jk3j7x1 
        foreign key (trainingDecisions_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_LEVEL_UP_TRAINING_DECISIONS 
        add constraint FK_lkuxpl4qspogc1n057w8gq208 
        foreign key (T_LEVELUP_ID) 
        references T_LEVELUP (ID);

    alter table T_MAGIC_OBJECT_T_MAGIC_OBJECT_BONUS 
        add constraint FK_h8abfb792vwsl7s8bk57h6nfk 
        foreign key (bonus_ID) 
        references T_MAGIC_OBJECT_BONUS (ID);

    alter table T_MAGIC_OBJECT_T_MAGIC_OBJECT_BONUS 
        add constraint FK_1fhy2i4j6k3tpe4qa2ml1o973 
        foreign key (T_MAGIC_OBJECT_ID) 
        references T_MAGIC_OBJECT (ID);

    alter table T_PERKS 
        add constraint FK_g9wpaab49hqhf44m5yg8qn55s 
        foreign key (weakness_ID) 
        references T_PERKS (ID);

    alter table T_PERKS_DECISION_CATEGORY_BONUS_CHOSEN 
        add constraint FK_f1fjh72xeap6mctkfyviei7qy 
        foreign key (PerkDecision_ID) 
        references T_PERKS_DECISION (ID);

    alter table T_PERKS_DECISION_CATEGORY_RANKS_CHOSEN 
        add constraint FK_ed56v5ec3aiq9mmnv9k1djkfe 
        foreign key (PerkDecision_ID) 
        references T_PERKS_DECISION (ID);

    alter table T_PERKS_DECISION_COMMON_SKILLS_CHOSEN 
        add constraint FK_4oocuq82gxdbki49xmfpteikt 
        foreign key (PerkDecision_ID) 
        references T_PERKS_DECISION (ID);

    alter table T_PERKS_DECISION_SKILLS_BONUS_CHOSEN 
        add constraint FK_ns1rxxvksdcpff7ckgpmymjel 
        foreign key (PerkDecision_ID) 
        references T_PERKS_DECISION (ID);

    alter table T_PERKS_DECISION_SKILLS_RANKS_CHOSEN 
        add constraint FK_ei6fol3jyx2hvnr17x8ptrwns 
        foreign key (PerkDecision_ID) 
        references T_PERKS_DECISION (ID);

    alter table T_PROFESSION_COMMON_SKILLS_CHOSEN 
        add constraint FK_2raew6dquhw5mda7g7ukpjodc 
        foreign key (ProfessionDecisions_ID) 
        references T_PROFESSION_DECISIONS (ID);

    alter table T_PROFESSION_PROFESSIONAL_SKILLS_CHOSEN 
        add constraint FK_3nawdfjc26ba8fu1gwjiip7kp 
        foreign key (ProfessionDecisions_ID) 
        references T_PROFESSION_DECISIONS (ID);

    alter table T_PROFESSION_REALMS_OF_MAGIC_AVAILABLE 
        add constraint FK_a28bsrg0b5aje416kqoh9307e 
        foreign key (ProfessionalRealmsOfMagicOptions_ID) 
        references T_PROFESSIONS_REALMS_MAGIC (ID);

    alter table T_PROFESSION_RESTRICTED_SKILLS_CHOSEN 
        add constraint FK_gf55ebjk1v194n23twxc9v9wx 
        foreign key (ProfessionDecisions_ID) 
        references T_PROFESSION_DECISIONS (ID);

    alter table T_PROFESSION_WEAPON_COST_CHOSEN 
        add constraint FK_hyu9pvkqjkxlo6ldjah1i6mkk 
        foreign key (weaponsCost_ID) 
        references T_CATEGORY_COST (ID);

    alter table T_PROFESSION_WEAPON_COST_CHOSEN 
        add constraint FK_shfc02rjnvjc79t8k3256xaf7 
        foreign key (T_PROFESSION_DECISIONS_ID) 
        references T_PROFESSION_DECISIONS (ID);

    alter table T_RANK_COSTS 
        add constraint FK_69x0wc8v7hhn7cm9xxlp3ufjw 
        foreign key (CategoryCost_ID) 
        references T_CATEGORY_COST (ID);

    alter table T_ROLL_LIST 
        add constraint FK_aoc04hgu3c7snaanjdg9tdlq6 
        foreign key (rolls_ID) 
        references T_ROLL (ID);

    alter table T_ROLL_LIST 
        add constraint FK_ehkmut7e29mc7d4frcws27ui8 
        foreign key (T_ROLL_GROUP_ID) 
        references T_ROLL_GROUP (ID);

    alter table T_TRAINING_CATEGORIES 
        add constraint FK_18lm0woocm7rkt2so0nrim3ao 
        foreign key (TrainingCategoriesSelected_ID) 
        references T_TRAINING_CATEGORIES_SELECTED (ID);

    alter table T_TRAINING_CHARACTERISTICS_UPDATES 
        add constraint FK_fnar1y6mb6imcc4ik6n3h72y6 
        foreign key (characteristicsUpdates_ID) 
        references T_CHARACTERISTIC_ROLL_GROUP (ID);

    alter table T_TRAINING_CHARACTERISTICS_UPDATES 
        add constraint FK_h4k7fjq7243ufkniehfblk7sl 
        foreign key (T_TRAINING_DECISION_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_DECISION_CATEGORY_SELECTED 
        add constraint FK_dcgwqauprl9jdgym1rwfd8i3v 
        foreign key (categoriesSelected_ID) 
        references T_TRAINING_CATEGORIES_SELECTED (ID);

    alter table T_TRAINING_DECISION_CATEGORY_SELECTED 
        add constraint FK_jfqy4psrv51jc13552x13ru6f 
        foreign key (T_TRAINING_DECISION_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_DECISION_COMMON_SKILLS 
        add constraint FK_gfh74mxgos7okm8dxxjnv2c20 
        foreign key (TrainingDecision_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_DECISION_PROFESSIONAL_SKILLS 
        add constraint FK_s8xsqc4hrxk9qj2xsp4mceixm 
        foreign key (TrainingDecision_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_DECISION_RESTRICTED_SKILLS 
        add constraint FK_9weotku43oc2i92kkse6u7j2c 
        foreign key (TrainingDecision_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_DECISION_SKILLS_SELECTED 
        add constraint FK_5nc8p7y8l2d6jwmdevwemmrtx 
        foreign key (skillsSelected_ID) 
        references T_TRAINING_SKILLS_SELECTED (ID);

    alter table T_TRAINING_DECISION_SKILLS_SELECTED 
        add constraint FK_q2qlhc9q74eva8nvqkhnlqdlb 
        foreign key (T_TRAINING_DECISION_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_MAGIC_ITEMS 
        add constraint FK_onpxi6hsdqh3um18at3hno1tv 
        foreign key (magicItems_ID) 
        references T_MAGIC_OBJECT (ID);

    alter table T_TRAINING_MAGIC_ITEMS 
        add constraint FK_rywsdjfh4185qhuxy0212pkra 
        foreign key (T_TRAINING_DECISION_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_OBJECTS 
        add constraint FK_7ixq2spabhxg6hap4um27sj1y 
        foreign key (equipment_ID) 
        references T_TRAINING_OBJECT (ID);

    alter table T_TRAINING_OBJECTS 
        add constraint FK_o5udpgul4alabv13apa6ut630 
        foreign key (T_TRAINING_DECISION_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_SKILLS_RANKS_PER_SKILL 
        add constraint FK_7n68qdxkt6wke54dr4qm74384 
        foreign key (TrainingSkillsSelected_ID) 
        references T_TRAINING_SKILLS_SELECTED (ID);

    create table hibernate_sequences (
         sequence_name varchar(255),
         sequence_next_hi_value integer 
    );

	CREATE TABLE `hibernate_sequence` (
		`next_val` bigint(20) DEFAULT NULL
	);

	LOCK TABLES `hibernate_sequence` WRITE;
	INSERT INTO `hibernate_sequence` VALUES (1);
	UNLOCK TABLES;

