package wl.books.track.bookstrack.services;


import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidationService {


    private Pattern pattern = Pattern.compile("-?\\d+");


    public boolean validatePageAndLimit(String page, String limit){
        if( (page!= null && isNumeric(page)) && (limit!=null && isNumeric(limit)) ) {
            return true;
        }
        return false;
    }


   public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public boolean validateRating(int rating){
        if(rating<=5){
            return  true;
        }
        return false;
    }

    public boolean validateISBN(String isbn){
            boolean result = validateIsbn10(isbn);
            if(result){
                return true;
            }else{
              result =   validateIsbn13(isbn);
                    if(result){
                        return  true;
                    }else{
                        return false;
                    }
                }
            }



    private boolean validateIsbn10( String isbn )
    {
        if ( isbn == null )
        {
            return false;
        }

        //remove any hyphens
        isbn = isbn.replaceAll( "-", "" );

        //must be a 10 digit ISBN
        if ( isbn.length() != 10 )
        {
            return false;
        }

        try
        {
            int tot = 0;
            for ( int i = 0; i < 9; i++ )
            {
                int digit = Integer.parseInt( isbn.substring( i, i + 1 ) );
                tot += ((10 - i) * digit);
            }

            String checksum = Integer.toString( (11 - (tot % 11)) % 11 );
            if ( "10".equals( checksum ) )
            {
                checksum = "X";
            }

            return checksum.equals( isbn.substring( 9 ) );
        }
        catch ( NumberFormatException nfe )
        {
            //to catch invalid ISBNs that have non-numeric characters in them
            return false;
        }
    }


    private boolean validateIsbn13( String isbn )
    {
        if ( isbn == null )
        {
            return false;
        }

        //remove any hyphens
        isbn = isbn.replaceAll( "-", "" );

        //must be a 13 digit ISBN
        if ( isbn.length() != 13 )
        {
            return false;
        }

        try
        {
            int tot = 0;
            for ( int i = 0; i < 12; i++ )
            {
                int digit = Integer.parseInt( isbn.substring( i, i + 1 ) );
                tot += (i % 2 == 0) ? digit * 1 : digit * 3;
            }

            //checksum must be 0-9. If calculated as 10 then = 0
            int checksum = 10 - (tot % 10);
            if ( checksum == 10 )
            {
                checksum = 0;
            }

            return checksum == Integer.parseInt( isbn.substring( 12 ) );
        }
        catch ( NumberFormatException nfe )
        {
            //to catch invalid ISBNs that have non-numeric characters in them
            return false;
        }
    }


}
