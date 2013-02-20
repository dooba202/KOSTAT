--<ScriptOptions statementTerminator=";"/>

CREATE TABLE "DB2ADMIN"."CCNCA_USER" (
		"USER_ID" VARCHAR(100) NOT NULL,
		"USER_TYPE" VARCHAR(10) NOT NULL,
		"ORG_ID" VARCHAR(10) NOT NULL,
		"ORG_NM" VARCHAR(50) NOT NULL
	);

CREATE UNIQUE INDEX "DB2ADMIN"."CC1354520092852" ON "DB2ADMIN"."CCNCA_USER" ("USER_ID" ASC);

ALTER TABLE "DB2ADMIN"."CCNCA_USER" ADD CONSTRAINT "CC1354520092852" PRIMARY KEY ("USER_ID");

