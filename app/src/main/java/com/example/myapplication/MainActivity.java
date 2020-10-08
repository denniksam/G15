package com.example.myapplication;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.activity_main ) ;

        findViewById( R.id.layout )
            .setOnTouchListener(
                new OnSwipeTouchListener(MainActivity.this )
        {
            public void onSwipeBottom( ) {
                if( canMoveBottom( ) ) {
                    moveBottom( ) ;
                    if( isFinish( ) ) {
                        endOfGame( ) ;
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.no_move), Toast.LENGTH_SHORT ).show( );
                }
            }
            public void onSwipeLeft( ) {
                if( canMoveLeft( ) ) {
                    moveLeft( ) ;
                    if( isFinish( ) ) {
                        endOfGame( ) ;
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.no_move), Toast.LENGTH_SHORT ).show( );
                }
            }
            public void onSwipeRight( ) {
                if( canMoveRight( ) ) {
                    moveRight( ) ;
                    if( isFinish( ) ) {
                        endOfGame( ) ;
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.no_move), Toast.LENGTH_SHORT ).show( );
                }
            }
            public void onSwipeTop( ) {
                if( canMoveTop( ) ) {
                    moveTop( ) ;
                    if( isFinish( ) ) {
                        endOfGame( ) ;
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.no_move), Toast.LENGTH_SHORT ).show( );
                }
            }
        });

        shuffle( ) ;

    }

    private void shuffle( ) {
        Random rnd = new Random( ) ;
        int n = 0 ;
        do {  // 100 random moves
            switch( rnd.nextInt( 4 ) ) {
                case 0 :
                    if( canMoveBottom( ) ) {
                        moveBottom( ) ;
                        ++n ;
                    }
                    break ;
                case 1 :
                    if( canMoveLeft( ) ) {
                        moveLeft( ) ;
                        ++n ;
                    }
                    break ;
                case 2 :
                    if( canMoveRight( ) ) {
                        moveRight( ) ;
                        ++n ;
                    }
                    break ;
                default :
                    if( canMoveTop( ) ) {
                        moveTop( ) ;
                        ++n ;
                    }
            }
        } while( n < 202 ) ;
    }

    private TextView getCellByNum( int num ) {
        int cellId =
            getResources( )
            .getIdentifier(
                    "cell" + num ,
                    "id" ,
                    getPackageName( )
            ) ;
        return findViewById( cellId ) ;
    }

    private int getEmptyCellIndex( ) {
        for( int i = 0; i < 16; ++i ) {
            if( "".equals( getCellByNum( i ).getText( ) ) ) {
                return i ;
            }
        }
        return  -1 ;
    }

    private boolean canMoveBottom( ) {
        if( getEmptyCellIndex( ) < 4 ) {  // Empty cell on top
            return false ;
        }
        return true ;
    }

    private boolean canMoveLeft( ) {
        if( getEmptyCellIndex( ) % 4 == 3 ) {  // Empty cell on right side
            return false ;
        }
        return true ;
    }

    private boolean canMoveRight( ) {
        if( getEmptyCellIndex( ) % 4 == 0 ) {  // Empty cell on left side
            return false ;
        }
        return true ;
    }

    private boolean canMoveTop( ) {
        if( getEmptyCellIndex( ) > 11 ) {  // Empty cell on bottom
            return false ;
        }
        return true ;
    }

    private void swapCells( TextView curCell, TextView newCell ) {
        CharSequence
                curVal = curCell.getText( ) ,
                newVal = newCell.getText( ) ;
        Drawable
                curBg  = curCell.getBackground( ) ,
                newBg  = newCell.getBackground( ) ;

        newCell.setText( curVal ) ;
        curCell.setText( newVal ) ;
        newCell.setBackgroundDrawable( curBg ) ;
        curCell.setBackgroundDrawable( newBg ) ;
    }

    private void moveLeft( ) {
        int emptyCellIndex = getEmptyCellIndex( ) ;
        swapCells(
            getCellByNum( emptyCellIndex ) ,
            getCellByNum( emptyCellIndex + 1 )
        ) ;
    }

    private void moveRight( ) {
        int emptyCellIndex = getEmptyCellIndex( ) ;
        swapCells(
                getCellByNum( emptyCellIndex ) ,
                getCellByNum( emptyCellIndex - 1 )
        ) ;
    }

    private void moveTop( ) {
        int emptyCellIndex = getEmptyCellIndex( ) ;
        swapCells(
                getCellByNum( emptyCellIndex ) ,
                getCellByNum( emptyCellIndex + 4 )
        ) ;
    }

    private void moveBottom( ) {
        int emptyCellIndex = getEmptyCellIndex( ) ;
        swapCells(
                getCellByNum( emptyCellIndex ) ,
                getCellByNum( emptyCellIndex - 4 )
        ) ;
    }


    private  boolean isFinish( ) {
        for( int i = 0; i < 15; ++i ) {
            if( ! String.valueOf( i + 1 )
                    .equals( getCellByNum( i ).getText( ) ) ) {
                return false ;
            }
        }
        return true ;
    }

    private  void endOfGame( ) {
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert )
                .setTitle( getString( R.string.victory ) )
                .setMessage( getString( R.string.play_again ) )
                .setIcon( android.R.drawable.ic_dialog_info )
                .setPositiveButton( R.string.once_more, new DialogInterface.OnClickListener( ) {
                    public void onClick( DialogInterface dialog, int whichButton ) {
                        shuffle( ) ;
                    }})
                .setNegativeButton( R.string.exit, new DialogInterface.OnClickListener( ) {
                    public void onClick( DialogInterface dialog, int whichButton ) {
                        finish( ) ;
                        System.exit(0 ) ;
                    }})
                .show( ) ;
    }

}
