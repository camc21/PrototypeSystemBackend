package br.com.carlos.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.carlos.record.AnimeRecord;

public class AnimeRecordTest {
	
	private static final Logger logger = LoggerFactory.getLogger(AnimeRecordTest.class);
	
	private AnimeRecord createAnimeRecord() {

        return new AnimeRecord(1L, "Demon Slayer", (short) 1, Boolean.TRUE);
    }
	
	@Test
    public void equalsTest() {
		AnimeRecord anime1 = createAnimeRecord();
		AnimeRecord anime2 = createAnimeRecord();

        assertTrue(anime1.equals(anime2));
        assertEquals(anime1, anime2);
        assertEquals(anime1.hashCode(), anime2.hashCode());
    }
	
	@Test
    public void toStringTest() {
        AnimeRecord anime1 = createAnimeRecord();
        logger.info(anime1.toString());

        assertEquals("AnimeRecord[idAnime=1, nome=Demon Slayer, 1, false",
                anime1.toString());
    }
	
	@Test
    public void accessorTest() {
        AnimeRecord anime1 = createAnimeRecord();
        assertEquals("Demon Slayer", anime1.nome());
    }

}
