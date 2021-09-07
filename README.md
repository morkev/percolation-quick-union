# Percolation-Quick-Union

Write a program to estimate the value of the percolation threshold via Monte Carlo simulation. 
- <i>N-by-N</i> grids of sites (each square represents a site).
- White site is open; black site is closed.
- Each site is open with probability <i>p</i> (or blocked with probability <i>1-p</i>).
- System percolates <i>iff</i> top and bottom are connected by open sites.

<img width="760" alt="percolation" src="https://user-images.githubusercontent.com/83437383/132085681-bddd311b-e394-4a2f-8f39-564ec090d379.png">

> Percolation visualization.

A system percolates if you can find a way to get from the top to the bottom through white squares. This is an example of a mathematical model where the problem is very well articulated. What's that threshold value but, nobody knows the solution to that mathematical problem. The only solution we have comes from a computational model, where we run simulations to try and determine the value of that probability. Those simulations are only enabled by fast union-find algorithms – that's our motivating example for why we might need fast union find algorithms, so let's look at that. So what we're going to run is called a so called Monte Carlo simulation, where we initialize the whole grid to be blocked (i.e., filled with black sites) and then we randomly fill in open sites.

Dynamic connectivity solution to estimate percolation threshold:

- Create an object for each site and name them <i>0</i> to <i>(N<sup>2</sup>) - 1</i>.
- Sites are in same component if connected by open sites.
- Percolates iff any site on bottom row is connected to any site on top row.
  - ... brute-force algorithm: <i>N<sup>2</sup></i> calls to <i>connected()</i>.

Clever trick: 

- Introduce 2 virtual sites (and connections to top and bottom).
- Percolates <i>iff</i> virtual top site is connected to virtual bottom site.
  - ... more efficient algorithm: only <i>1</i> call to <i>connected()</i>.

<img width="746" alt="virtualTB" src="https://user-images.githubusercontent.com/83437383/132085686-2559745e-cecc-4f51-ba30-89b8a8ee68f6.png">

> Representation of a virtual top site connected to a virtual bottom site.

What we want to do is connect, check whether any site in the bottom row is connected to any site in the top row, and use union find for that. The problem is that it would be a brute force algorithm – would be quadratic, because it would have <i>N<sup>2</sup></i> calls to find, and check whether they're connected. For each site on the top, I'd check each site on the bottom, but still too slow. Instead, what we do is create a virtual site on the top and on the bottom. Then, when we want to know whether this system percolates, we just check whether the virtual top site is connected to the virtual bottom site.

So how do we model opening a new site? Well to open a site we just connect it to all its adjacent open sites. So that's a few calls to union but that's easy to implement. And then with that simple relationship we can use exactly the code that we developed to go ahead and run a simulation for this connectivity problem. That's where we get the result that, by running enough simulations for a big-enough n, that this, percolation threshold is about.

So, what is percolation threshold <i>p<sup>*</sup></i>? About <i>0.592746</i> for large square lattices – a constant known only via simulation. Fast algorithms enable accurate answers to scientific question. If we use a slow union-find algorithm we won't be able to run it for very big problems, and we won't get a very accurate answer. 
