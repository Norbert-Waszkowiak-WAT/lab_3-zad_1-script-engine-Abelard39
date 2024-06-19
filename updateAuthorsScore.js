(function() {
    var authors = authorRepository.findAll();

    authors.forEach(function(author) {
        var score = scoreService.getScore(author);

        author.score = score;

        authorRepository.save(author);
    });

    return 'Authors scores updated successfully';
})();