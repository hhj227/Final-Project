#define DATABASE_NAME		"db1"
#define DATABASE_USERNAME	"root"
#define DATABASE_PASSWORD	"jsy123"
MYSQL *mysql1;

#include <mysql/mysql.h>

//*****************************************
//*****************************************
//********** CONNECT TO DATABASE **********
//*****************************************
//*****************************************
void mysql_connect (void)
{
    //initialize MYSQL object for connections
	mysql1 = mysql_init(NULL);

    if(mysql1 == NULL)
    {
        fprintf(stderr, "%s\n", mysql_error(mysql1));
        return;
    }

    //Connect to the database
    if(mysql_real_connect(mysql1, "localhost", root, jsy123, db1, 0, NULL, 0) == NULL)
    {
    	fprintf(stderr, "%s\n", mysql_error(mysql1));
    }
    else
    {
        printf("Database connection successful.\n");
    }
}



//**********************************************
//**********************************************
//********** DISCONNECT FROM DATABASE **********
//**********************************************
//**********************************************
void mysql_disconnect (void)
{
    mysql_close(mysql1);
    printf( "Disconnected from database.\n");
}
