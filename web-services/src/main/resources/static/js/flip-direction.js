async function flipDirection() {
    await fetch("/api/flip-translation-direction", {method:"post"});

    window.location.reload();
}