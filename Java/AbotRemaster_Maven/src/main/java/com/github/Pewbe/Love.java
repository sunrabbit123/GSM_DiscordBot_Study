package com.github.Pewbe;

public class Love implements Comparable<Love>{
    long userId;
    int love;

    public Love( String love, String userId ){
        this.userId = Long.parseLong( userId );
        this.love = Integer.parseInt( love );
    }
    public long getUserId(){
        return userId;
    }
    public int getLove(){
        return love;
    }

    @Override
    public int compareTo( Love l ) {
        if( this.love < l.love )
            return 1;
        else if ( this.love > l.love )
            return -1;
        else
            return 0;
    }
}