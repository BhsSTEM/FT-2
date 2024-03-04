package database;

import android.provider.BaseColumns;

public final class FeedReaderContract
{
    private FeedReaderContract(){ }

    public static final class LoginTableEntries implements BaseColumns
    {
        public static final String TABLE_NAME = "UserLogin";
        public static final String EMAIL_COLUMN = "Email";
    }
}
