Main.class: Main.java users.db
	@echo Compiling exercise program...
	@javac Main.java

users.db: create_db.sql
	@echo Creating sample database...
	@rm -f ./users.db
	@cat create_db.sql | sqlite3 ./users.db

clean:
	@echo Removing output files...
	@rm -f ./Main.class ./users.db

