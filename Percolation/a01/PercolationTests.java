package a01;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Functionality and Performance 
 * Requirements –> Criteria
 * 
 * Additional jUnit tests created to test 
 * whether the functional and performance 
 * requirements are met. Expected behavior 
 * has been described in each test.
 * 
 * @author Kevin Mora
 * @author Jordan Bramhall
 */
public class PercolationTests {

    private static final int GRID_SIZE = 5;
    private Percolation percolation;

    @Before
    public void setUp() {
        percolation = new Percolation(GRID_SIZE);
    }

    @Test
    public void shouldOpenAGrid() {
        // given
        assertFalse(percolation.isOpen(1, 1));
        // when
        percolation.open(1, 1);
        // then
        assertTrue(percolation.isOpen(1, 1));
    }

    @Test
    public void shouldPercolate() {
        // when
        percolation.open(4, 2);
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(4, 2);
        // then
        assertFalse(percolation.percolates());
    }

    @Test
    public void shouldNotPercolate() {
        // when
        percolation.open(1, 1);
        percolation.open(1, 2);
        // then
        assertFalse(percolation.percolates());
    }

    @Test
    public void shouldBeFull() {
        // when
        percolation.open(1, 1);
        percolation.open(1, 2);
        // then
        assertFalse(percolation.isFull(1, 2));
    }

    @Test
    public void shouldNotBeFull() {
        // when
        percolation.open(3, 2);
        // then
        assertFalse(percolation.isFull(3, 2));
    }

    @Test
    public void shouldNotPercolateInCornerCase() {
        Percolation percolation = new Percolation(1);
        assertTrue(percolation.percolates());
    }

    @Test
    (expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfGridSizeIsNegative() {
        new Percolation(-5);
    }

    @Test
    (expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionIfOpenIsCalledWithFieldOutsideTheGrid() {
        percolation.open(GRID_SIZE+1, GRID_SIZE+1);
    }

    @Test
    (expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionIfIsOpenIsCalledWithFieldOutsideTheGrid() {
        percolation.isOpen(GRID_SIZE + 1, GRID_SIZE + 1);
    }

    @Test
    (expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionIfIsFullIsCalledWithFieldOutsideTheGrid() {
        percolation.isFull(GRID_SIZE + 1, GRID_SIZE + 1);
    }
    
}
