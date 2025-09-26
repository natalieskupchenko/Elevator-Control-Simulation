# Propositional Logic Assistant: CPSC 210 Term Project

## By: Natalie Skupchenko

### Phase 0

#### Task 2
**What will the application do?**
This application allows users to enter propositional logic formulas and analyze them using various logical operations. Users will also be able to visualize the propositional logic formulas through truth tables and semantic tableaus (trees). Users will be able to enter a single formula or multiple formulas. Users will then be able to operate on these formulas as specified below:

*Single Formula Input*:
- Generate a truth table
- Generate a truth table of the negation
- Check tautology
- Check contradiction
- Check contingency

*Multiple Formula Input*:
- Check entailment (Γ ⊨ φ)
- Check equivalence (φ ≡ ψ)
- Check consistency (can all formulas be true simultaneously?)
- Build semantic tableau/truth tree
- Show multi-formula truth table 
- Show counterexample (if applicable)

**Who will use it?**
Students of logic, mathematics, or computer science who want an interactive tool to better understand logical relationships. A large portion of this application is parallel with the course content of PHIL 220 at UBC.

**Why is this project of interest to you?**
I am a mathematics student and I am very interested in both symbolic logic and computer science. This project allows me to combine my interests in both of these subjects, which will help me to deepen my understanding of both logic and Java. This project can also provide me with an educational tool that may help in my further studies in logic. 

**Non-Trivial Classes**
<ol>
  <li>Formula</li>
This will represent a single propositional logic formula. It will parse expressions, evaluate truth under assignments and extract variables. It will also define the basic logical operators (AND, OR, NOT, XOR, implication etc.). These operators will be used to evaluate individual formulas based on truth assignments.
  <li>KnowledgeBase</li>
This will store multiple formulas. It will allow adding/removing formulas, listing formulas and retrieving formulas for operations.
  <li>Operations</li>
This will define operations like tautology, entailment, equivalence, consistency etc. and work with one or more formulas to apply logical reasoning rules using these operations.
  <li>Display</li>
</ol>

#### Task 3
**User Stories**

<ol>
  <li>Single Formula Input
    <ol>
      <li>As a user, I want to input a single propositional logic formula so that I can analyze its properties.</li>
      <li>As a user, I want to generate a truth table for a single formula so that I can see all possible truth values.</li>
      <li>As a user, I want to generate a truth table for the negation of a single formula so that I can understand its inverse behaviour.</li>
      <li>As a user, I want to check if a formula is a tautology, contradiction, or contingency so that I can better understand its logical behaiour.</li>
    </ol>
  </li>
  <li>Multiple Formula Input
    <ol>
      <li>As a user, I want to input multiple formulas so that I can analyze relationships between them.</li>
      <li>As a user, I want to check if one formula entails another so that I can determine logical consequences. </li>
      <li>As a user I want to check if two formulas are equivalent so that I can see if they always have the same truth values.</li>
      <li>As a user, I want to check if a set of formulas is consistent so that I can verify that all formulas can be true at the same time.</li>
      <li>As a user, I want to generate a semantic tableau (a truth tree) for a set of formulas so that I can visually analyze their satisfiability. </li>
      <li>As a user, I want to generate a multi-formula truth table to see the truth values for several formulas at once. </li>
      <li>As a user, I want to view counterexamples when operations fail so that I can understand where the issue lies. (e.g. when formulas are not equivalent, or consistent) </li>
    </ol>
  </li>
    <li>Additional/Error Cases
    <ol>
      <li>As a user, I want to be able to clear the formulas I've entered so that I can start fresh with new formulas. </li>
      <li> As a user, I want to be able to name or label any formulas so that I can easily identify them in my KnowledgeBase. </li>
      <li>As a user, I want to be able to view a list of formulas I have entered so that I can keep track of them. </li>
      <li>As a user, I want to system to prompt me if I try to enter an empty formula or no formulas at all so that I don't get empty or undefined results. </li>
      <li>As a user, I want to receive an error message if I input an invalid formula so that I know what went wrong. </li>
    </ol>
  </li>
</ol>


