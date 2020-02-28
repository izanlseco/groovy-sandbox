def groovyBook = new BookBean()

groovyBook.setTitle('Groovy in Action')
assert groovyBook.getTitle() == 'Groovy in Action'

groovyBook.title = 'Groovy conquers the world'
assert groovyBook.getTitle() == 'Groovy conquers the world'
